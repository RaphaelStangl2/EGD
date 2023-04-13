package com.example.egd.ui.getStarted

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


import com.example.egd.R
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.ProgressBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetStarted(viewModel: EGDViewModel, onRegistered: () -> Unit, modifier: Modifier, onBluetoothStateChanged:()->Unit, sharedPreference: () -> SharedPreferences?
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    val uiState = viewModel.getStartedUiState.collectAsState().value
    val loginUiState = viewModel.loginUiState.collectAsState().value

    var step = uiState.step
    var numberOfSteps = uiState.numberOfSteps
    var egdDevice = uiState.EGDDevice
    var carName = uiState.carName
    var fuelConsumption = uiState.averageCarConsumption
    var userName = uiState.userName
    var email = uiState.email
    var password = uiState.password
    var response = uiState.response
    var friendSearchBarContent = uiState.friendSearchBarContent
    var assignedFriendsList = uiState.assignedFriendsList
    var searchedFriendsList = uiState.searchFriendsList


    var passwordVisibility = loginUiState.passwordVisibility



    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_baseline_visibility_24)
    else
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)



    Column(
        modifier = modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxSize()
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
                ConnectScreen(viewModel, onBluetoothStateChanged)
            }
            else if(step == 3) {
                CarInfoScreen(carName, fuelConsumption, viewModel)
            }
            else if (step == 4){
                AddUserScreen(viewModel = viewModel, friendSearchBarContent, assignedFriendsList, searchedFriendsList)
            }
            else if(step == 5) {
                RegisterScreen(viewModel = viewModel, userName = userName, email = email, password = password, passwordVisibility = passwordVisibility, icon = icon, response = response)
            }


        } else if (numberOfSteps == 3) {
            if (step == 3){
                RegisterScreen(viewModel = viewModel, userName = userName, email = email, password = password, passwordVisibility = passwordVisibility, icon = icon, response = response)
            }
        }

        if (step == 2 || step == 4) {
            Spacer(modifier = Modifier.weight(1f))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        if (step + 1 > numberOfSteps){
                            viewModel.setCarName("")
                            viewModel.setFuelConsumption("")
                            viewModel.checkRegister(onRegistered, sharedPreference)

                            //onRegistered()

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






