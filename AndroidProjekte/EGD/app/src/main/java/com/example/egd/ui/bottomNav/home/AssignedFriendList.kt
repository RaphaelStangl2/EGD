package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.example.egd.ui.EGDViewModel

@Composable
fun AssignedFriendList(
    assignedFriendsList: Array<User>?,
    car: Car,
    addFriendsList: Array<User>?,
    viewModel: EGDViewModel,
    goToFriendsAddScreen: () -> Unit
){
    val homeUiStateVal = viewModel.homeUiState.collectAsState().value
    if (assignedFriendsList != null) {
        for (assignedFriend in assignedFriendsList){
            if (assignedFriend != homeUiStateVal.user){
                Row(){
                    UserCard(assignedFriend,true ,viewModel)
                }
            }
        }
        if (addFriendsList != null) {
            for (addFriend in addFriendsList){
                Row(){
                    UserCard(addFriend, false, viewModel)
                }
            }
        }
        if (car.isAdmin == true){
            Row() {
                AddUserCard(goToFriendsAddScreen)
            }
        }
    }
}