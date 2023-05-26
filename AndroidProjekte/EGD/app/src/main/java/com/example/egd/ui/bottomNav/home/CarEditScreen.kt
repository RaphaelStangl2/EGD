package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@Composable
fun CarEditScreen(onUpdate: () -> Unit, viewModel: EGDViewModel, modifier: Modifier){

    val carUiState = viewModel.editCarUiState.collectAsState().value
    val homeUiState = viewModel.homeUiState.collectAsState().value
    val car = carUiState.car
    val assignedFriendsList = homeUiState.assignedFriendsList
    val searchFriendList = homeUiState.searchFriendList

    viewModel.getUsersForCar()

    if (car != null)
    {
        val carName = carUiState.name
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
                Text(text="Car Name:")
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
                    isError = !validationCarName.valid && triedToSubmit
                )
            }

            Row(){
                if (!validationCarName.valid && triedToSubmit){
                    ErrorText(message = validationCarName.message)
                }
            }
            Spacer(modifier = Modifier.height(7.dp))

            Row(){
                Text(text="Average fuel consumption per 100 km:")
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
                    isError = !validationFuelConsumption.valid && triedToSubmit
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
                Text(text="Friends:")
            }

            AssignedFriendList(assignedFriendsList = assignedFriendsList)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        viewModel.setTriedToSubmitEdit(true)
                        viewModel.updateCar(onUpdate)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Update")
                }
            }

        }
        }
    }

