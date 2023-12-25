package com.example.egd.ui.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.egd.R
import com.example.egd.data.entities.User

@Composable
fun UserInfo(user: User){
    Row(){
        Icon(tint = Color.White, modifier = Modifier
            .fillMaxWidth(1F)
            .fillMaxSize(0.3F), painter = painterResource(id = R.drawable.ic_baseline_account_circle_24), contentDescription =  "User Icon")
    }
    Row (){
        if (user != null) {
            Text(user.userName, color = Color.White)
        }
    }
    Row(){
        if (user != null) {
            Text(user.email, color = Color.White)
        }
    }
}