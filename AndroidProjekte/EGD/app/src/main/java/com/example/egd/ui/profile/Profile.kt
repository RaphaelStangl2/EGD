package com.example.egd.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
    var deleteInvitationList = invitationState.deleteInvitationList

    val user = homeScreenState.user

    Column() {

        Row(modifier = Modifier.background(color = MaterialTheme.colors.primary)){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally){
                if (user != null) {
                    UserInfo(user = user)
                }
            }
        }
        Row(Modifier.padding(15.dp)){
            Column() {

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Button(onClick = { logout() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background), elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    ) {
                        Icon(tint = Color.LightGray, painter = painterResource(id = R.drawable.ic_baseline_exit_to_app_24), contentDescription = "Logout")
                        Text("Log Out", color = Color.LightGray)
                    }
                    IconButton(onClick = { viewModel.deleteInvitations() }, enabled = deleteInvitationList != null&& deleteInvitationList.isNotEmpty()) {
                        Icon(tint = if (deleteInvitationList == null)
                            Color.LightGray
                            else
                                MaterialTheme.colors.error,painter = painterResource(id = R.drawable.ic_baseline_delete_outline_24), contentDescription = "delete invitations")
                    }
                }
                Divider(color = Color.LightGray)
                Row() {
                    Column(){
                        Text("Invitations")
                        InvitationList(statusInvitationList, incomingInvitationList, viewModel)
                    }
                }

            }
        }
    }
}