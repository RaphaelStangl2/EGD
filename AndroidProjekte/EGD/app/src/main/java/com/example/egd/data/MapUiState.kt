package com.example.egd.data

import android.location.Location
import com.google.android.gms.maps.model.CameraPosition

data class MapUiState(
    val startLocation: Location? = null,
    val searchBarContent: String = "",
    val doNotShowRational: Boolean = false,
    val getCars: Boolean = false
)
