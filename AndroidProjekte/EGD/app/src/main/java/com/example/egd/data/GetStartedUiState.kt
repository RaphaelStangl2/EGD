package com.example.egd.data

import com.example.egd.data.entities.User

data class GetStartedUiState(
    var step: Int = 1,
    var numberOfSteps: Int = 5,
    val EGDDevice: Boolean = true,
    val carName: String = "",
    val licensePlate: String = "",
    val averageCarConsumption: String = "",
    val email: String = "",
    val password: String = "",
    val userName:String = "",
    val doNotShowRational: Boolean = false,
    val response:String = "",
    val friendSearchBarContent:String = "",
    val triedToSubmit:Boolean = false,
    val connectionSuccessful:Boolean = false,
    val currentUUID: String = "",
    val buttonClicked: Boolean = false,
    val accidentCode: String = ""
)
