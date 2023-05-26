package com.example.egd.data.ble

import android.app.Service
import android.content.Intent
import android.os.IBinder
import javax.inject.Inject

class BLEBackgroundService @Inject constructor(val bleService: BLEService): Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {

    }


}