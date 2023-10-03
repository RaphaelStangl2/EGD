package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.egd.data.entities.User
import com.example.egd.ui.EGDViewModel

@Composable
fun AssignedFriendList(
    assignedFriendsList: Array<User>?,
    addFriendsList: Array<User>?,
    viewModel: EGDViewModel,
    goToFriendsAddScreen: () -> Unit
){
    if (assignedFriendsList != null) {
        for (assignedFriend in assignedFriendsList){
            Row(){
                UserCard(assignedFriend,true ,viewModel)
            }
        }
        if (addFriendsList != null) {
            for (addFriend in addFriendsList){
                Row(){
                    UserCard(addFriend, false, viewModel)
                }
            }
        }
        Row() {
            AddUserCard(goToFriendsAddScreen)
        }
    }
}