package com.example.egd.ui

import androidx.compose.foundation.layout.*

import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun GetStarted(viewModel: EGDViewModel, modifier: Modifier) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    val uiState = viewModel.getStartedUiState.collectAsState().value

    var step = uiState.step

    Column(
        modifier = modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxWidth()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Row(){
            ProgressBar(progress = step, numberOfSteps = 5, progressBarWidth)
        }


        DeviceSelectionFields()


        Row(verticalAlignment = Alignment.Bottom){
            Button(onClick = {viewModel.setStep(step+1)}, modifier = Modifier.fillMaxWidth()){
                Text(text = "NEXT")
            }
        }

    }

}


@Composable
fun DeviceSelectionFields(){
    Row(){
        Text(text="Do you have a EGD Device?")

    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = false, onClick = { /*TODO*/ })

        Text(text="Yes")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = false, onClick = { /*TODO*/ })
        Text(text="No")
    }
}