package com.example.ble_test

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ble_test.ui.BLEViewModel
import com.example.ble_test.ui.ConnectScreen
import com.example.ble_test.ui.DataScreen


enum class BLEScreen() {
    Start,
    Data
}

@Composable
fun BLEApp(modifier: Modifier = Modifier,
           viewModel: BLEViewModel = viewModel(),
           onBluetoothStateChanged:()->Unit
){

    val navController = rememberNavController()

    Scaffold(
        /*topBar = {
            CupcakeAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }*/
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BLEScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = BLEScreen.Start.name) {
                ConnectScreen(
                    onNextButtonClicked = {
                        navController.navigate(BLEScreen.Data.name)
                    }
                )
            }

            composable(route =BLEScreen.Data.name){
                DataScreen(viewModel = viewModel, bleUiState = uiState, onBluetoothStateChanged = {

                    onBluetoothStateChanged()
                })

            }

        }
        // TODO: add NavHost
    }

}
