package com.example.egd.data.ble

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.example.egd.MainActivity
import com.example.egd.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BLEService () : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"
    val workManager = WorkManager.getInstance(application)

    @Inject
    lateinit var bleReceiveManager: BLEReceiveManager

    companion object {
        fun startService(context: MainActivity, message: String) {
            val startIntent = Intent(context, BLEService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, BLEService::class.java)
            context.stopService(stopIntent)
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        bleReceiveManager?.startReceiving()


        //val intent = Intent(this, BLEService::class.java) // Build the intent for the service
        //applicationContext.startForegroundService(intent)


        //do heavy work on a background thread
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("EGD BLE Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_baseline_directions_car_24)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        //stopSelf();
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

}