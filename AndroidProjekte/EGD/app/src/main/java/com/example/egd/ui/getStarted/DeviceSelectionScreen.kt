package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.egd.ui.EGDViewModel

@Composable
fun DeviceSelectionFields(viewModel: EGDViewModel, hasEgdDevice: Boolean) {
    Row() {
        Text(text = "Do you have a EGD Device?")

    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = hasEgdDevice,
            onClick = {

                if (!hasEgdDevice) {
                    viewModel.setEGDDevice(!hasEgdDevice)
                }
                viewModel.setNumberOfSteps(5)
            },
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
        )
        Text(text = "Yes")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = !hasEgdDevice,
            onClick = {
                if (hasEgdDevice){
                    viewModel.setEGDDevice(!hasEgdDevice)
                }
                viewModel.setNumberOfSteps(3)
            },
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
        )
        Text(text = "No")
    }
}