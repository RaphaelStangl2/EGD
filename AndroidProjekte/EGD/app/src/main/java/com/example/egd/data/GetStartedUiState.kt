package com.example.egd.data

import com.example.egd.data.entities.User

data class GetStartedUiState(
    val step: Int = 1,
    val numberOfSteps: Int = 5,
    val EGDDevice: Boolean = true,
    val carName: String = "",
    val averageCarConsumption: String = "",
    val email: String = "",
    val password: String = "",
    val userName:String = "",
    val doNotShowRational: Boolean = false,
    val response:String = "",
    val friendSearchBarContent:String = "",
    val assignedFriendsList: Array<User>? = null,
    val searchFriendsList: Array<User>? = null

)
