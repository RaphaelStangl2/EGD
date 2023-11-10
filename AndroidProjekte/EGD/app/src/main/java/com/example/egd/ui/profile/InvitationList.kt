package com.example.egd.ui.profile

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.egd.data.entities.Invitation

@Composable
fun InvitationList(
    statusInvitationList: Array<Invitation>?,
    incomingInvitationList: Array<Invitation>?
) {
    val scrollState = rememberScrollState()

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large){

        Column(Modifier.fillMaxHeight().verticalScroll(scrollState)){
            if (incomingInvitationList == null && statusInvitationList== null){
                Row(){
                    Text("No invitation activities", color = Color.LightGray)
                }
            }
            if (incomingInvitationList != null) {
                for (incomingInvitation in incomingInvitationList){
                    Row(){
                        IncomingInvitation(incomingInvitation)
                    }
                }
            }
            if (statusInvitationList != null){
                for (statusInvitation in statusInvitationList){
                    Row(){
                        StatusInvitation(statusInvitation)
                    }
                }
            }

        }
    }
}