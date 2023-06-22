package com.example.egd.ui.validation

import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorText(message:String){
    Text(
        text = message,
        color = MaterialTheme.colors.error
    )
}