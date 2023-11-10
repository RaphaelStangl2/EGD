package com.example.egd.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.egd.R
import com.example.egd.ui.EGDViewModel

@Composable
fun Profile(logout: () -> Unit, modifier: Modifier, viewModel: EGDViewModel){
    val invitationState = viewModel.invitation.collectAsState().value
    val homeScreenState = viewModel.homeUiState.collectAsState().value

    var statusInvitationList = invitationState.statusInvitationList
    var incomingInvitationList = invitationState.incomingInvitationList

    val user = homeScreenState.user

    Column() {

        Row(modifier = Modifier.background(color = MaterialTheme.colors.primary)){
            Column(modifier = Modifier.fillMaxWidth().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally){
                if (user != null) {
                    UserInfo(user = user)
                }
            }
        }
        Row(Modifier.padding(15.dp)){
            Column() {
                Row(){
                    Button(onClick = { logout() }) {
                        Text("Log Out")
                    }
                }
                Row() {
                    Column(){
                        Text("Invitations")
                        InvitationList(statusInvitationList, incomingInvitationList)
                    }
                }
            }
        }
    }
}