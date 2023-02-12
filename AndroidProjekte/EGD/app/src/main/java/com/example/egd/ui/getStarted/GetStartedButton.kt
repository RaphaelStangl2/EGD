package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GetStartedButton(   onButtonClicked: () -> Unit = {}, text:String){
    Button(onClick = {onButtonClicked()}, modifier = Modifier
        .fillMaxWidth()
        .height(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = MaterialTheme.colors.primary ),
        elevation = elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp)
    ){
        Text(text= text, fontWeight = FontWeight.Bold)
    }
}

