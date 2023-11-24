package com.example.egd.ui.bottomNav.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.egd.data.entities.Car
import com.example.egd.ui.EGDViewModel

@Composable
fun CarEditScreen(onUpdate: () -> Unit, viewModel: EGDViewModel, modifier: Modifier){

    val carUiState = viewModel.editCarUiState.collectAsState().value
    val car = carUiState.car

    if (car != null)
    {
        val carName = carUiState.name
        val carConsumption = carUiState.consumption

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
                        viewModel.setCar(car)
                    },
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
                    value = carConsumption.toString(),
                    onValueChange =
                    {
                        car.consumption = it.toDouble()
                        viewModel.setCar(car)
                    },
                    placeholder = { Text(text= "7") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    modifier = Modifier.width(77.dp)
                )
                Text(text="liters")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Update")
                }
            }

        }
        }
    }

