package com.example.egd.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.R

@Composable
fun NoInvitationsIcon(){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Icon(contentDescription = "no Invitation Icon", tint = Color.LightGray, painter = painterResource(id = R.drawable.ic_baseline_forward_to_inbox_24), modifier = Modifier.height(200.dp).width(200.dp))
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Text("No invitation activity", color = Color.LightGray, style = TextStyle(fontSize = 20.sp)) // Use sp for text size)
    }
}