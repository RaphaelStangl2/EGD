package com.example.egd.data

data class GetStartedUiState(
    val step: Int = 1,
    val numberOfSteps: Int = 5,
    val EGDDevice: Boolean = true,
    val carName: String = "",
    val averageCarConsumption: String = "",
    val email: String = "",
    val password: String = "",
    val userName:String = "",
    val doNotShowRational: Boolean = false
)
