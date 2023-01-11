package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
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
    var carName = uiState.carName
    var fuelConsumption = uiState.averageCarConsumption



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
                CarInfoScreen(carName, fuelConsumption, viewModel)
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
fun CarInfoScreen(carName:String, fuelConsumption: String, viewModel: EGDViewModel) {
    Row(){
        Text(text="Car Name:")
    }
    Row(){
        TextField(
            value = carName,
            onValueChange = {viewModel.setCarName(it)},
            placeholder = {Text(text="My Car")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row(){
        Text(text="Average fuel consumption per 100 km:")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            value = fuelConsumption.toString(),
            onValueChange = {viewModel.setFuelConsumption(it)},
            placeholder = {Text(text= "7")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier.width(77.dp)
        )
        Text(text="liters")
    }
}
