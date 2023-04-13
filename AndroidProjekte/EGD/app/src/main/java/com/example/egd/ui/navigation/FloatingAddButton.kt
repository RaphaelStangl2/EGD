package com.example.egd.ui.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.egd.R
import com.example.egd.ui.EGDViewModel

@Composable
fun FLoatingAddButton(viewModel: EGDViewModel, navigateToAddCarScreen: () -> Unit,
){
    FloatingActionButton(onClick = { navigateToAddCarScreen() },
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(contentDescription = "AddActionButton", painter = painterResource(id = R.drawable.ic_baseline_add_24))

    }
}
