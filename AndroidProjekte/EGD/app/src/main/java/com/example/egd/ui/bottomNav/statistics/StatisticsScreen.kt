package com.example.egd.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.egd.ui.bottomNav.statistics.CircleDiagram

@Composable
fun StatisticsScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier
){
    val connectionSuccessful = viewModel.getStartedUiState.collectAsState().value.connectionSuccessful


    Column(){
        Button(onClick = {}){
            Text(text="Statistics Screen")
        }


        Text(text=connectionSuccessful.toString())
    }

}

@Preview
@Composable
fun StatisticsScreenPreview(){
    CircleDiagram()
}