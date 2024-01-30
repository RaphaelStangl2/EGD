package com.example.egd.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.Invitation
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.theme.ProgressBar
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun StatusInvitation(statusInvitation: Invitation, viewModel:EGDViewModel) {
    val status = statusInvitation.status
    var deleteArray = viewModel.invitation.collectAsState().value.deleteInvitationList
    var deleteList: MutableList<Invitation>? = null
     if (deleteArray != null){
          deleteList = deleteArray!!.toMutableList()
     }
    var isRed by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            if (deleteArray == null){
                deleteArray = arrayOf(statusInvitation)
                isRed = true
                viewModel.addInvitationToDeleteList(deleteArray)
            }
            else if (deleteList != null)
                if (isRed == false){
                    isRed = true
                    deleteList!!.add(statusInvitation)
                    viewModel.addInvitationToDeleteList(deleteList!!.toTypedArray())
                } else{
                    isRed = false
                    deleteList!!.remove(statusInvitation)
                    //TODO delete invitation from array
                    if (deleteList!!.size == 0){
                        viewModel.addInvitationToDeleteList(null)
                    }else{
                        viewModel.addInvitationToDeleteList(deleteList!!.toTypedArray()!!)
                    }
                }

                   },
        backgroundColor = MaterialTheme.colors.background,
        border =  if (isRed){
            BorderStroke(2.dp, color = MaterialTheme.colors.error)
        } else{
            BorderStroke(0.dp, color = Color.LightGray)
        },
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large)
        {


        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_info_24),
                        contentDescription = "info",
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(50.dp)
                    )
                }
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    //Text(getMessageForStatus(status, statusInvitation.userToInvite.userName))

                    Row(){
                        Text(text= "Invitation: ")
                    }
                    Row(){
                        Text(text=statusInvitation.userToInvite.userName)
                    }
                }

                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(text = "$status", color = ProgressBar)
                }
            }

        }
    }
}

fun getMessageForStatus(status:String, userName: String): String{
    if (status == "agree"){
        return userName + " has accepted your invitation"
    }
    if (status == "dismiss"){
        return userName + " has declined your invitation"
    }
    if (status == "waiting"){
        return userName + " hasn't yet responded to your invitation"
    }
    else
    {
        return "Status unknown"
    }
}

//@Composable
//@Preview
//fun PreviewStatusInvitation(){
//    var invitation = Invitation(1, User(id = 1, userName = "Test User", email="", password = ""),
//        UserCar(user = User(id = 1, userName = "Test 2 User", email="", password = ""),
//            car = Car(1, name= "Test Car", consumption = 5.0, latitude = 0.0, longitude = 0.0, uuid="", licensePlate = "", currentUser = null, null),
//            isAdmin = true
//        ), status="agree")
//    //StatusInvitation(statusInvitation = invitation, viewModel =)
//}