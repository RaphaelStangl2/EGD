package com.example.egd.data

import com.google.android.gms.maps.model.LatLng

data class DriveState(
    var startPosition: LatLng? = null,
    var kilometers: Double = 0.0
)
