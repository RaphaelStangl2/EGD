package com.example.egd.data

import androidx.compose.foundation.ScrollState
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User

data class HomeScreenState(
    val searchBarContent: String = "",
    val cars: Array<Car>? = null,
    val searchFriendList: Array<User>? = null,
    val friendSearchBarContent:String = "",
    val assignedFriendsList: Array<User>? = null
)
