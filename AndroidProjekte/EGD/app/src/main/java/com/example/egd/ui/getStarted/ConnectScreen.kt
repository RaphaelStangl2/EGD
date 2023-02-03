package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConnectScreen() {
    Row(){
        Text(text="Connect to your EGD Device")
    }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().height(500.dp)){

        Button(onClick = {}, modifier = Modifier.size(150.dp, 150.dp), shape = RoundedCornerShape(75.dp)){
            Text(text="CONNECT")
        }
    }
}