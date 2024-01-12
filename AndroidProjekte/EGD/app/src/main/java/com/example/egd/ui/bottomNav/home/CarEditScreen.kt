package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.dialogues.AccidentDialogue
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@Composable
fun CarEditScreen(onUpdate: () -> Unit, viewModel: EGDViewModel, goToFriendsAddScreen: ()-> Unit, modifier: Modifier){

    val carUiState = viewModel.editCarUiState.collectAsState().value
    val homeUiState = viewModel.homeUiState.collectAsState().value
    val car = carUiState.car
    val assignedEditFriendsList = carUiState.assignedFriendList
    val addFriendsList = carUiState.addFriendList
    val searchFriendList = homeUiState.searchFriendList

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if (event == Lifecycle.Event.ON_CREATE){
                    if (assignedEditFriendsList == null){
                        viewModel.getUsersForCar()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )


    if (car != null)
    {
        val carName = carUiState.name
        val licensePlate = carUiState.licensePlate
        val carConsumption = carUiState.consumption

        val validationService = ValidationService()

        val validationCarName = validationService.validateCarName(carName)
        val validationFuelConsumption = validationService.validateFuelConsumption(carConsumption)
        val triedToSubmit: Boolean = carUiState.triedToSubmit

        Column(
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize()
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(){
                Text(text="Car Name:", fontWeight = FontWeight.Bold )
            }
            Row(){
                TextField(
                    value = carName,
                    onValueChange =
                    {
                        car.name = it
                        viewModel.setCar(car, car.consumption.toString())
                    },
                    placeholder = { Text(text="My Car") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    isError = !validationCarName.valid && triedToSubmit,
                    enabled = car.isAdmin ?: false
                )
            }

            Row(){
                if (!validationCarName.valid && triedToSubmit){
                    ErrorText(message = validationCarName.message)
                }
            }
            Spacer(modifier = Modifier.height(7.dp))

            Row(){
                Text(text="Licence plate:", fontWeight = FontWeight.Bold)
            }

            Row(){
                TextField(
                    value = licensePlate,
                    onValueChange =
                    {
                        car.licensePlate = it
                        viewModel.setCar(car, car.consumption.toString())
                    },
                    placeholder = { Text(text="STK1234") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    enabled = car.isAdmin ?: false
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Row(){
                Text(text="Average fuel consumption per 100 km:", fontWeight = FontWeight.Bold)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                TextField(
                    value = carConsumption,
                    onValueChange =
                    {
                        viewModel.setCar(car, it)
                    },
                    placeholder = { Text(text= "7") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier.width(77.dp),
                    isError = !validationFuelConsumption.valid && triedToSubmit,
                    enabled = car.isAdmin ?: false
                )
                Text(text="liters")
            }

            Row(){
                if (!validationFuelConsumption.valid && triedToSubmit){
                    ErrorText(message = validationFuelConsumption.message)
                }
            }
            Spacer(modifier = Modifier.height(7.dp))

            Row(){
                Text(text="Friends:", fontWeight = FontWeight.Bold)
            }

            AssignedFriendList(assignedFriendsList = assignedEditFriendsList, car, addFriendsList, viewModel, goToFriendsAddScreen)

            if (car.isAdmin == true){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            viewModel.setTriedToSubmitEdit(true)
                            viewModel.updateCar(onUpdate)
                            viewModel.setAssignedEditFriendsList(null)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}

