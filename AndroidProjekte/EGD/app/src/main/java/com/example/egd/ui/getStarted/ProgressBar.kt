package com.example.egd.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(progress: Int, numberOfSteps: Int, progressBarWidth: Dp){
    val dividerWidth = (progressBarWidth/numberOfSteps) - 6.dp

    Row(){
        for (i in 1..numberOfSteps) {
            val color = if (i <= progress)
                com.example.egd.ui.theme.ProgressBar
            else
                Color.LightGray

            Divider(
                thickness = 5.dp,
                modifier = Modifier.width(dividerWidth),
                color = color
            )
            Text(" ")
        }
    }
}