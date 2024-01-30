package com.example.egd.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.Invitation
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import com.example.egd.ui.EGDViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun IncomingInvitation(incomingInvitation: Invitation, viewModel: EGDViewModel) {

    val user = incomingInvitation.userCar.user
    val car = incomingInvitation.userCar.car

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_circle_right_24),
                        contentDescription = "arrow right",
                        modifier = Modifier.fillMaxHeight().size(50.dp)
                    )
                }
                Column(modifier = Modifier.fillMaxHeight(),verticalArrangement = Arrangement.Center) {
                    Text(text = user.userName, fontWeight = FontWeight.Bold)
                    Text(text = car.name)

                }

                Column() {
                    Button(
                        onClick = { viewModel.updateInvitatioStatus(invitationV = incomingInvitation, status = "dismiss") },
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Gray
                        )
                    ) {
                        Text(text = "Dismiss")
                    }
                    Button(
                        onClick = { viewModel.updateInvitatioStatus(invitationV = incomingInvitation, status = "agree") },
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = com.example.egd.ui.theme.ProgressBar,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Accept")
                    }
                }
            }

        }
    }
}

//@Composable
//@Preview
//fun PreviewIncomingInvitation(){
//    var invitation = Invitation(1, User(id = 1, userName = "Test User", email="", password = ""),
//        UserCar(user = User(id = 1, userName = "Test 2 User", email="", password = ""),
//            car = Car(1, name= "Test Car", consumption = 5.0, latitude = 0.0, longitude = 0.0, uuid="", licensePlate = "", currentUser = null, null),
//            isAdmin = true
//        ), status="agree")
//    //IncomingInvitation(incomingInvitation = invitation)
//}