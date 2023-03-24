package com.example.egd.data

import androidx.compose.foundation.ScrollState
import com.example.egd.data.entities.Car

data class HomeScreenState(
    val searchBarContent: String = "",
    val cars: Array<Car>? = null
)
