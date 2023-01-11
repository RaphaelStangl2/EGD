package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*

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
    var numberOfSteps = uiState.numberOfSteps
    var egdDevice = uiState.EGDDevice


    Column(
        modifier = modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxWidth()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Row() {
            ProgressBar(progress = step, numberOfSteps = numberOfSteps, progressBarWidth)
        }

        if (step == 1) {
            DeviceSelectionFields(viewModel, egdDevice)
        } else if (numberOfSteps == 5) {
            if (step == 2){
                ConnectScreen()
            }
            if(step == 3) {
                CarInfoScreen()
            }

        } else if (numberOfSteps == 3) {

        }


            Row(verticalAlignment = Alignment.Bottom) {
                Button(
                    onClick = { viewModel.setStep(step + 1) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "NEXT")
                }
            }

        }

    }



    @Composable
    fun DeviceSelectionFields(viewModel: EGDViewModel, hasEgdDevice: Boolean) {
        Row() {
            Text(text = "Do you have a EGD Device?")

        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = hasEgdDevice,
                onClick = {

                    if (!hasEgdDevice) {
                        viewModel.setEGDDevice(!hasEgdDevice)
                    }
                    viewModel.setNumberOfSteps(5)
                },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
            )
            Text(text = "Yes")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !hasEgdDevice,
                onClick = {
                    if (hasEgdDevice){
                        viewModel.setEGDDevice(!hasEgdDevice)
                    }
                    viewModel.setNumberOfSteps(3)
                },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
            )
            Text(text = "No")
        }
    }

@Composable
fun ConnectScreen() {
    Row(){
        Text(text="Connect to your EGD Device")
    }
    Row(){
        Button(onClick = {}){
            Text(text="CONNECT")
        }
    }
}

@Composable
fun CarInfoScreen() {
    Row(){
        Text(text="Car Name:")
    }
    Row(){
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {Text(text="My Hyundai Car")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row(){
        Text(text="Average fuel consumption per 100 km:")
    }
    Row(){
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {Text(text="5l")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
    }
}
