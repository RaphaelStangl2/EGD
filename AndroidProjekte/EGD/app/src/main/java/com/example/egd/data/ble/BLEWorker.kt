package com.example.egd.data.ble

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import javax.inject.Inject

class BLEWorker @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager,
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params)
{

    override fun doWork(): Result {

        bleReceiveManager.startReceiving()
        return Result.success()
    }

}