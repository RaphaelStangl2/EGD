package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.R

@Composable
fun NoCarsIcon(){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

        Image(contentDescription = "no Cars Icon", painter = painterResource(id = R.drawable.car_crash ), modifier = Modifier.height(200.dp).width(200.dp))
    }

    Row(){
        Text("There are no cars assigned to you yet", color = Color.LightGray, style = TextStyle(fontSize = 20.sp)) // Use sp for text size)
    }
}