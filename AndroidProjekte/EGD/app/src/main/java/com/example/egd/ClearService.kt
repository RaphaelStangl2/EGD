package com.example.egd

import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.content.Intent
import android.os.IBinder
import android.util.Log


class ClearService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ClearService", "Service Started")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ClearService", "Service Destroyed")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e("ClearService", "END")

        stopSelf()
    }
}