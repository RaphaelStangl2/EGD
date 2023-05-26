package com.example.egd.ui.bottomNav.home

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.egd.data.entities.User

@Composable
fun AddUserCard(){
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 10.dp)
                .heightIn(max = 50.dp),
            backgroundColor = Color.White,
            //elevation = 5.dp,
            shape = MaterialTheme.shapes.large
        ) {
            Column(){
                Button(
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background, contentColor = MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.shadow(0.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    shape= MaterialTheme.shapes.large
                ){
                    Text("Add Friend", color = MaterialTheme.colors.primaryVariant, fontWeight = MaterialTheme.typography.body2.fontWeight)
                }
            }
        }

}