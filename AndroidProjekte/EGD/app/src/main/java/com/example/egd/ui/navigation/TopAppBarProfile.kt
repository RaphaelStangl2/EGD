package com.example.egd.ui.navigation

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.example.egd.ui.EGDViewModel

@Composable
fun TopAppBarProfile(viewModel: EGDViewModel){
    TopAppBar(
        title = { Text("") },
        actions = {
            //SearchBarHome(searchBarContent = "", viewModel = viewModel)
        }
    )
}