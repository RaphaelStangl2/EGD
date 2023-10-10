package com.example.egd

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.work.WorkManager
import com.example.egd.data.ble.BLEReceiveManager
import com.example.egd.data.ble.BLEService
import com.example.egd.ui.theme.EGDTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter
    val workManager = WorkManager.getInstance(application)

    @Inject
    lateinit var bleReceiveManager: BLEReceiveManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            EGDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    EGDApp( startForegroundService = {startForeground()},onNoInternetConnection = {isInternetAvailable(this)}, onBluetoothStateChanged = {showBluetoothDialog()}, onGPSRequired = {showGPSDialog()}, sharedPreference = {getEmailSharedPreferences()}, context= this) { stopForeground() }
                }
            }
        }
    }


   override fun onStop() {
       bleReceiveManager.closeConnection()
       bleReceiveManager.disconnect()
        super.onStop()
    }


    override fun onDestroy() {
        //val workManager = WorkManager.getInstance(application)
        //workManager.enqueue(OneTimeWorkRequest.from(BLEWorker::class.java))

        bleReceiveManager.closeConnection()
        bleReceiveManager.disconnect()
        Toast.makeText(this, "CLose Connection", Toast.LENGTH_SHORT).show()

        super.onDestroy()



//        BLEService.startService(this, "BLE Service is running")

    }

    fun startForeground(){
        BLEService.startService(this, "BLE Service is running")
    }

    fun stopForeground(){
        BLEService.stopService(this)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {

        BLEService.stopService(this)


        //val intent = Intent(this, BLEService::class.java) // Build the intent for the service
        //applicationContext.startForegroundService(intent)

        super.onStart()

        //showGPSDialog()
        //showBluetoothDialog()
    }

    private var isBluetootDialogAlreadyShown = false
    private var isGPSDialogAlreadyShown = false
    private fun showBluetoothDialog(){
        if(!bluetoothAdapter.isEnabled){
            if(!isBluetootDialogAlreadyShown){
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startBluetoothIntentForResult.launch(enableBluetoothIntent)
                isBluetootDialogAlreadyShown = true
            }
        }
    }

    private fun getEmailSharedPreferences(): SharedPreferences? {
        val sharedPreference =  this.getSharedPreferences(
            getString(R.string.egdEmailReference), Context.MODE_PRIVATE)
        return sharedPreference

    }

    private fun showGPSDialog(){
        val enableGPSIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //val enableGPSIntent = Intent(Intent.ACTION_VIEW);

        startGPSIntentForResult.launch(enableGPSIntent)
        isGPSDialogAlreadyShown = true
    }

    private val startGPSIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            isGPSDialogAlreadyShown = false
            if(result.resultCode != Activity.RESULT_OK){
                showGPSDialog()
            }
        }

    private val startBluetoothIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            isBluetootDialogAlreadyShown = false
            if(result.resultCode != Activity.RESULT_OK){
                showBluetoothDialog()
            }
        }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}

