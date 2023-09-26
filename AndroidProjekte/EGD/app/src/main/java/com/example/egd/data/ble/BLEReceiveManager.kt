package com.example.egd.data.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.egd.MainActivity
import com.example.egd.data.EGDUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@SuppressLint("MissingPermission")
class BLEReceiveManager @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val context: Context
) {

    private val DEVICE_NAME = "EGD-SOS"
    private val CHARACTERISTICS_UUID = "1688a0c8-6cc9-11ed-a1eb-0242ac120002"
    private val CCCD_DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805F9B34FB"

    private var connectedServiceUUID = ""
    private var initializeConnection: Boolean = false
    private var serviceUUIDList:Array<String?>? = emptyArray()
    private var tmpUUID:String = ""
    val mutex = Mutex()


    val data: MutableSharedFlow<EGDUiState> = MutableSharedFlow()

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private var gatt: BluetoothGatt? = null

    private var isScanning = false

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun setInitializeConnection(initializeConnectionVar: Boolean){
        initializeConnection = initializeConnectionVar
    }

    fun setUUIDList(serviceUUIDList:Array<String?>?){
        this.serviceUUIDList = serviceUUIDList
    }

    private val scanCallback = object : ScanCallback(){

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if(result.device.name == DEVICE_NAME){
                if(isScanning){
                    Toast.makeText(context, "Device Found", Toast.LENGTH_SHORT).show()
                    result.device.connectGatt(context,true, gattCallback)
                    isScanning = false
                    bleScanner.stopScan(this)
                }
            }
        }
    }

    private var currentConnectionAttempt = 1
    private var MAXIMUM_CONNECTION_ATTEMPTS = 5

    private val gattCallback = object : BluetoothGattCallback(){
        @SuppressLint("SuspiciousIndentation")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if(status == BluetoothGatt.GATT_SUCCESS){
                if(newState == BluetoothProfile.STATE_CONNECTED){
                    //Toast.makeText(context, "State Connected", Toast.LENGTH_SHORT).show()
                    gatt.discoverServices()
                    this@BLEReceiveManager.gatt = gatt
                } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                    //Toast.makeText(context, "State Connected", Toast.LENGTH_SHORT).show()

                    coroutineScope.launch {
                        data.emit(
                            EGDUiState(false, "0","")
                        )
                    }
                    gatt.close()

                    startReceiving()
                }
            }else{
                //Toast.makeText(context, "Gatt closed", Toast.LENGTH_SHORT).show()

                gatt.close()
                currentConnectionAttempt+=1

               // if(currentConnectionAttempt<=MAXIMUM_CONNECTION_ATTEMPTS){
                    startReceiving()
                //}else{
                //}
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            with(gatt){
                requestMtu(517)
            }
        }

        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            var characteristic:BluetoothGattCharacteristic? = null
            for (i in serviceUUIDList!!){
                characteristic = i?.let { findCharacteristics(it, CHARACTERISTICS_UUID) }
                if (characteristic != null){
                    break
                }
            }
            if (characteristic == null){
                return
            }

            enableNotification(characteristic)

            coroutineScope.launch {
                data.emit(
                    EGDUiState(true, "0", "")
                )
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            with(characteristic){
                //when(uuid){
                    //UUID.fromString(CHARACTERISTICS_UUID) -> {
                        //XX XX XX XX XX XX
                        val test: String = value.decodeToString()

                        val boolean: Boolean = true
                        /*val multiplicator = if(value.first().toInt()> 0) -1 else 1
                        val temperature = value[1].toInt() + value[2].toInt() / 10f
                        val humidity = value[4].toInt() + value[5].toInt() / 10f
                        val tempHumidityResult = TempHumidityResult(
                            multiplicator * temperature,
                            humidity,
                            ConnectionState.Connected
                        )
                        coroutineScope.launch {
                            data.emit(
                                Resource.Success(data = test)
                            )
                        }*/
                        if (tmpUUID != null){
                            coroutineScope.launch {
                                data.emit(
                                    EGDUiState(boolean, test, tmpUUID)
                                )
                            }
                        }
                        else{
                            coroutineScope.launch {
                                data.emit(
                                    EGDUiState(boolean, test, "")
                                )
                            }
                        }
                    //}
                    //else -> Unit
                //}
            }
        }


    }

    fun startReceiving() {
        Toast.makeText(context, "Start Receiving", Toast.LENGTH_SHORT).show()

        isScanning = true
        bleScanner.startScan(null,scanSettings,scanCallback)
    }

    private fun findCharacteristics(serviceUUID: String, characteristicsUUID:String):BluetoothGattCharacteristic?{
        /*return gatt?.services?.find { service ->
            service.uuid.toString() == serviceUUID
        }?.characteristics?.find { characteristics ->
            characteristics.uuid.toString() == characteristicsUUID
        }*/
        if (initializeConnection == true){
            tmpUUID =gatt?.services?.get(2)?.uuid.toString()
            connectedServiceUUID = tmpUUID
            initializeConnection = false
        }
        else if (gatt?.services?.get(2)?.uuid.toString() != serviceUUID)
        {
            return null
        }

        return gatt?.services?.get(2)?.characteristics?.get(0)
    }

    private fun enableNotification(characteristic: BluetoothGattCharacteristic){
        val cccdUuid = UUID.fromString(CCCD_DESCRIPTOR_UUID)
        val payload = when {
            characteristic.isIndicatable() -> BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
            characteristic.isNotifiable() -> BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            else -> return
        }

        characteristic.getDescriptor(cccdUuid)?.let { cccdDescriptor ->
            if(gatt?.setCharacteristicNotification(characteristic, true) == false){
                Log.d("BLEReceiveManager","set characteristics notification failed")
                return
            }
            writeDescription(cccdDescriptor, payload)
        }
    }

    private fun writeDescription(descriptor: BluetoothGattDescriptor, payload: ByteArray){
        gatt?.let { gatt ->
            descriptor.value = payload
            gatt.writeDescriptor(descriptor)
        } ?: error("Not connected to a BLE device!")
    }

    fun reconnect() {
        gatt?.connect()
    }

    fun closeConnection() {
        bleScanner.stopScan(scanCallback)
        val characteristic = findCharacteristics(connectedServiceUUID, CHARACTERISTICS_UUID)
        if(characteristic != null){
            disconnectCharacteristic(characteristic)
        }
        gatt?.close()
    }

    private fun disconnectCharacteristic(characteristic: BluetoothGattCharacteristic){
        val cccdUuid = UUID.fromString(CCCD_DESCRIPTOR_UUID)
        characteristic.getDescriptor(cccdUuid)?.let { cccdDescriptor ->
            if(gatt?.setCharacteristicNotification(characteristic,false) == false){
                Log.d("TempHumidReceiveManager","set charateristics notification failed")
                return
            }
            writeDescription(cccdDescriptor, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
        }
    }

    fun disconnect() {
        gatt?.disconnect()
    }
}