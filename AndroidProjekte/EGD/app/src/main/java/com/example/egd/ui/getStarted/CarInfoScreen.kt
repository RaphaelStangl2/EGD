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

@Composable
fun CarInfoScreen(carName:String, fuelConsumption: String, viewModel: EGDViewModel) {
    Row(){
        Text(text="Car Name:")
    }
    Row(){
        TextField(
            value = carName,
            onValueChange = {viewModel.setCarName(it)},
            placeholder = { Text(text="My Car") },
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
            placeholder = { Text(text= "7") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier.width(77.dp)
        )
        Text(text="liters")
    }
}