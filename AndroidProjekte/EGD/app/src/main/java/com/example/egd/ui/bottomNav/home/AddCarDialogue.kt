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
import com.example.egd.R
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.ProgressBar
import com.example.egd.ui.getStarted.AddUserScreen
import com.example.egd.ui.getStarted.CarInfoScreen
import com.example.egd.ui.getStarted.ConnectScreen
import com.example.egd.ui.validation.ValidationService
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddCarDialogue(viewModel: EGDViewModel, onAdded: () -> Unit,modifier: Modifier, onBluetoothStateChanged:()->Unit){
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    val homeUiState = viewModel.homeUiState.collectAsState().value
    val searchFriendList = homeUiState.searchFriendList
    var assignedFriendList = homeUiState.assignedFriendsList

    val loginUiState = viewModel.loginUiState.collectAsState().value
    val carUiState = viewModel.getStartedUiState.collectAsState().value

    viewModel.setNumberOfSteps(3)

    val validationService = ValidationService()

    val step = carUiState.step
    val numberOfSteps = carUiState.numberOfSteps
    var carName = carUiState.carName
    var fuelConsumption = carUiState.averageCarConsumption
    var userName = carUiState.userName
    var email = carUiState.email
    var password = carUiState.password
    var licensePlate = carUiState.licensePlate
    var triedToSubmit = carUiState.triedToSubmit
    var searchBarContent = carUiState.friendSearchBarContent


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
            ConnectScreen(viewModel, onBluetoothStateChanged, validationService, triedToSubmit)
        }
        else if(step == 2) {
            CarInfoScreen(carName, fuelConsumption, viewModel, triedToSubmit, licensePlate)
        }
        else if(step == 3) {
           AddUserScreen(
               viewModel = viewModel,
               friendSearchBarContent = searchBarContent,
               assignedFriendsList = assignedFriendList,
               searchedFriendsList = searchFriendList,
               modifier = Modifier
           )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    if (step + 1 > numberOfSteps){

                        viewModel.addCar(onAdded = onAdded)

                        onAdded()
                    } else if (step == 2){
                        if (validationService.validateCarInfoScreen(carName, fuelConsumption)){
                            viewModel.setStep(step + 1)
                            viewModel.setTriedToSubmit(false)
                        }else{
                            viewModel.setTriedToSubmit(true)
                        }
                    }
                    else{
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
