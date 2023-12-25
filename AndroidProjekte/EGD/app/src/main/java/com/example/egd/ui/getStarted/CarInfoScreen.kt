package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@Composable
fun CarInfoScreen(carName:String, fuelConsumption: String, viewModel: EGDViewModel, triedToSubmit: Boolean, licensePlate:String) {
    val validationService = ValidationService()

    val validationCarName = validationService.validateCarName(carName)
    val validationFuelConsumption = validationService.validateFuelConsumption(fuelConsumption)
    val validationLicensePlate = validationService.validateLicencePlate(licensePlate)

    Row(){
        Text(text="Car Name:")
    }
    Row(){
        TextField(
            value = carName,
            onValueChange = {viewModel.setCarName(it)},
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
    Row{
        Text(text="Licence Plate")
    }

    Row(){
        TextField(
            value = licensePlate,
            onValueChange = {viewModel.setLicensePlate(it)},
            placeholder = { Text(text="STK1234") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            isError = !validationLicensePlate.valid && triedToSubmit
        )
    }
    Row(){
        if (!validationLicensePlate.valid && triedToSubmit){
            ErrorText(message = validationLicensePlate.message)
        }
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row{
        Text(text="Average fuel consumption per 100 km:")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            value = fuelConsumption.toString(),
            onValueChange = {viewModel.setFuelConsumption(it)},
            placeholder = { Text(text= "7") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier.width(77.dp),
            isError = !validationFuelConsumption.valid && triedToSubmit
        )
        Text(text="liters")
    }
    Row{
        if (!validationFuelConsumption.valid && triedToSubmit){
            ErrorText(message = validationFuelConsumption.message)
        }
    }
}
