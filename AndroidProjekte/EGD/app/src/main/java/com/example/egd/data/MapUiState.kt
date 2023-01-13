package com.example.egd.data

import com.google.android.gms.maps.model.LatLng

data class MapUiState(
    val showMap: Boolean = false,
    //val userLocation: LatLng,
    val searchBarContent: String = ""
)
