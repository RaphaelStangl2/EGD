package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.example.egd.ui.EGDViewModel


@Composable
fun UserCard(user: User,assignedFriend:Boolean, viewModel:EGDViewModel, car: Car){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 10.dp)
            .heightIn(max=50.dp),
        backgroundColor = Color.White,
        //elevation = 5.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(){
            Row(verticalAlignment = Alignment.CenterVertically){
                Row(modifier = Modifier.fillMaxWidth(0.5f), horizontalArrangement = Arrangement.Start){
                    Icon(contentDescription = "", painter = painterResource(id = R.drawable.ic_baseline_person_24))
                    Text(text =user.userName)
                }
                if (car.isAdmin == true){
                    Row(modifier = Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.End){
                        Button(modifier = Modifier.padding(end=4.dp),
                            onClick = {
                                if (assignedFriend){
                                    viewModel.removeUserFromAssignedEditFriendsList(user)
                                    viewModel.addUserToRemovedFriendsList(user)
                                }
                                else {
                                    viewModel.removeUserFromAddList(user)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error), shape = MaterialTheme.shapes.large){
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }
}