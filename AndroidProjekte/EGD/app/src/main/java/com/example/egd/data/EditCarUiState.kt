package com.example.egd.data

import com.example.egd.data.entities.Car

data class EditCarUiState(
    val car: Car? = null,
    val name: String = "",
    val consumption: String = ""
)
