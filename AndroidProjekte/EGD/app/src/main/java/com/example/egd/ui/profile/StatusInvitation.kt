package com.example.egd.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.Invitation
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun StatusInvitation(statusInvitation: Invitation) {
    val status = statusInvitation.status
    Column(){
        Row(){
            Icon(painter = painterResource(id = R.drawable.ic_baseline_info_24), contentDescription = "info")
            Text(text="Invite Status", fontWeight = FontWeight.Light)
        }
        Row(){
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(getMessageForStatus(status, statusInvitation.userToInvite.userName))
            }
            Column() {
                Text(text= "Status: $status", color = com.example.egd.ui.theme.ProgressBar)
            }
        }
    }
}

fun getMessageForStatus(status:String, userName: String): String{
    if (status == "agree"){
        return userName + "has accepted your invitation"
    }
    if (status == "dismiss"){
        return userName + "has declined your invitation"
    }
    if (status == "waiting"){
        return userName + "hasn't yet responded to your invitation"
    }
    else
    {
        return "Status unknown"
    }
}

@Composable
@Preview
fun PreviewStatusInvitation(){
    var invitation = Invitation(1, User(id = 1, userName = "Test User", email="", password = ""),
        UserCar(user = User(id = 1, userName = "Test 2 User", email="", password = ""),
            car = Car(1, name= "Test Car", consumption = 5.0, latitude = 0.0, longitude = 0.0, uuid="", licensePlate = "", currentUser = null),
            isAdmin = true
        ), status="agree")
    StatusInvitation(statusInvitation = invitation)
}