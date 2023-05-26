package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.egd.data.entities.User

@Composable
fun AssignedFriendList(assignedFriendsList: Array<User>?){
    if (assignedFriendsList != null) {
        for (assignedFriend in assignedFriendsList){
            Row(){
                UserCard(assignedFriend)
            }
        }
        Row() {
            AddUserCard()
        }
    }
}