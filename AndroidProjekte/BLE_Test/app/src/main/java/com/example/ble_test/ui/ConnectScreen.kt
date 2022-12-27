package com.example.ble_test.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun ConnectScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Card(modifier = Modifier.height(100.dp).width(100.dp)) {
            Button(
                onClick = {
                    onNextButtonClicked()
                },

                ) {
                Text(text = "Connect")
            }
        }
    }
}
