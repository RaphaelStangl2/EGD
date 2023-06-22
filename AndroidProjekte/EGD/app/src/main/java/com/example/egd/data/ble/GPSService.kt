package com.example.egd.data.ble

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import com.example.egd.data.EGDUiState
import com.example.egd.ui.EGDViewModel
import kotlinx.coroutines.launch

class GPSService(var viewModel: EGDViewModel, var setGPSData: (location:Location?) -> Unit) :
    Runnable {

    override fun run() {

        getUserPosition(viewModel){ location ->
            setGPSData(location)
        }
    }

    @SuppressLint("MissingPermission")
    fun getUserPosition(viewModel: EGDViewModel, callback: (Location?) -> Unit) {
        viewModel.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            callback(location)
        }.addOnFailureListener { exception ->
            // Handle failure
            callback(null)
        }
    }
}

