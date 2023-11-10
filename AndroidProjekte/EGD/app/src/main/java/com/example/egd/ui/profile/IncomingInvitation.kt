package com.example.egd.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.Invitation
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun IncomingInvitation(incomingInvitation: Invitation) {

    val user = incomingInvitation.userCar.user
    val car = incomingInvitation.userCar.car

    Column(){
        Row(){
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_right_24), contentDescription = "arrow right")
            Text(text="Invite")
        }
        Row(){
            Column() {
                Text(text=user.userName + "has invited you to " + car.name)
            }
            Column() {
                Button(onClick = { /*TODO*/ }) {
                    Text(text="Accept")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text="Dismiss")
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewIncomingInvitation(){
    var invitation = Invitation(1, User(id = 1, userName = "Test User", email="", password = ""),
        UserCar(user = User(id = 1, userName = "Test 2 User", email="", password = ""),
            car = Car(1, name= "Test Car", consumption = 5.0, latitude = 0.0, longitude = 0.0, uuid="", licensePlate = "", currentUser = null),
            isAdmin = true
        ), status="agree")
    StatusInvitation(statusInvitation = invitation)
}