package com.example.egd.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.example.egd.R
import com.example.egd.ui.EGDViewModel

@Composable
fun BluetoothIcon(viewModel: EGDViewModel){
    var connectionSuccessful = viewModel.getStartedUiState.collectAsState().value.connectionSuccessful

    if (connectionSuccessful){
        Icon(painter = painterResource(id = R.drawable.ic_baseline_bluetooth_24), contentDescription = "Bluetooth Successful Icon", tint = com.example.egd.ui.theme.ProgressBar)
    } else {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_bluetooth_disabled_24), contentDescription = "Bluetooth disabled Icon", tint = MaterialTheme.colors.error )
    }
}