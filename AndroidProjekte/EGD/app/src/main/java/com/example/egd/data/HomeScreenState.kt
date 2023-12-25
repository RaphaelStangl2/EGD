package com.example.egd.data

import androidx.compose.foundation.ScrollState
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User

data class HomeScreenState(
    val searchBarContent: String = "",
    var cars: Array<Car>? = null,
    var searchFriendList: Array<User>? = null,
    val friendSearchBarContent:String = "",
    var assignedFriendsList: Array<User>? = null,
    val user: User? = null,
    val deleteResponse:String = ""
)
