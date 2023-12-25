package com.example.egd.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.egd.data.entities.Invitation
import com.example.egd.ui.EGDViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun InvitationList(
    statusInvitationList: Array<Invitation>?,
    incomingInvitationList: Array<Invitation>?,
    viewModel:EGDViewModel
) {
    val scrollState = rememberScrollState()

    //Card(modifier = Modifier
    //    .fillMaxWidth()
    //    .padding(10.dp),
    //    backgroundColor = MaterialTheme.colors.background,
    //    elevation = 8.dp,
    //    shape = MaterialTheme.shapes.large){

        Column(
            Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)){
            var foundAgreeOrDismiss = false
            var visibleIncoming = false
            if (incomingInvitationList != null) {
                for (invitation in incomingInvitationList) {
                    if (invitation.status == "agree" || invitation.status == "dismiss") {
                        foundAgreeOrDismiss = true
                    }else{
                        visibleIncoming = true
                    }
                }
            }

            if ((incomingInvitationList.isNullOrEmpty()||foundAgreeOrDismiss && !visibleIncoming) && statusInvitationList.isNullOrEmpty()){
                Row() {
                    Column(){
                        NoInvitationsIcon()
                    }
                }
            }


            if (incomingInvitationList != null) {
                for (incomingInvitation in incomingInvitationList){
                    Row(){
                        if (incomingInvitation.status != "agree" && incomingInvitation.status != "dismiss"){
                            IncomingInvitation(incomingInvitation, viewModel)
                        }
                    }
                }
            }
            if (statusInvitationList != null){
                for (statusInvitation in statusInvitationList){
                    Row(){
                        StatusInvitation(statusInvitation, viewModel)
                    }
                }
            }

        }
    //}
}