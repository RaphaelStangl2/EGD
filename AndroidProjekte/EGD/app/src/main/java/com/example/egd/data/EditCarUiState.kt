package com.example.egd.data

import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User

data class EditCarUiState(
    val car: Car? = null,
    val name: String = "",
    val consumption: String = "",
    val id: Long? = null,
    val triedToSubmit: Boolean = false,
    var addFriendList: Array<User>? = null,
    var removeFriendList: Array<User>? = null,
    var assignedFriendList: Array<User>? = null

    )
