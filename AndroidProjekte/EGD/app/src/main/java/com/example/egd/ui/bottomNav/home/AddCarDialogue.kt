package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.egd.R
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.ProgressBar
import com.example.egd.ui.getStarted.CarInfoScreen
import com.example.egd.ui.getStarted.ConnectScreen
import com.example.egd.ui.getStarted.RegisterScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddCarDialogue(viewModel: EGDViewModel, onAdded: () -> Unit,modifier: Modifier, onBluetoothStateChanged:()->Unit){
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    val loginUiState = viewModel.loginUiState.collectAsState().value
    val carUiState = viewModel.getStartedUiState.collectAsState().value
    viewModel.setNumberOfSteps(3)

    val step = carUiState.step
    val numberOfSteps = carUiState.numberOfSteps
    var carName = carUiState.carName
    var fuelConsumption = carUiState.averageCarConsumption
    var userName = carUiState.userName
    var email = carUiState.email
    var password = carUiState.password

    var passwordVisibility = loginUiState.passwordVisibility

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_baseline_visibility_24)
    else
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)




    Column(
        modifier = Modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxSize()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Row() {
            ProgressBar(progress = step, numberOfSteps = numberOfSteps, progressBarWidth)
        }

        if (step == 1){
            ConnectScreen(viewModel, onBluetoothStateChanged)
        }
        else if(step == 2) {
            CarInfoScreen(carName, fuelConsumption, viewModel)
        }
        else if(step == 3) {
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    if (step + 1 > numberOfSteps){
                        viewModel.setCarName("")
                        viewModel.setFuelConsumption("")

                        onAdded()
                    }else{
                        viewModel.setStep(step + 1)
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "NEXT")
            }
        }

    }

}
