package com.example.egd.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

@Composable
fun StatisticsScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier
){
    var connection = true
    val connectionSuccessful = viewModel.getStartedUiState.collectAsState().value.connectionSuccessful


    Column(){
        Button(onClick = {connection = false}){
            Text(text="Statistics Screen")
        }
        Text(text=connection.toString())

        Text(text=connectionSuccessful.toString())
    }

}