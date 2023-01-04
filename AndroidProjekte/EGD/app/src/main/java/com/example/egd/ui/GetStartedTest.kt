package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun GetStartedTest(viewModel: EGDViewModel, modifier: Modifier) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    Column(
        modifier = modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(7.dp))
        ProgressBar(progress = 1, numberOfSteps = 5, progressBarWidth)
    }
}