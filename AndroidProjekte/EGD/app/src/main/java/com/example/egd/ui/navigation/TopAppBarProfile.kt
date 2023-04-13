package com.example.egd.ui.navigation

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.example.egd.ui.EGDViewModel

@Composable
fun TopAppBarProfile(viewModel: EGDViewModel){
    TopAppBar(
        title = { Text("") },
        /*actions = {
            //SearchBarHome(searchBarContent = "", viewModel = viewModel)
            IconButton(onClick = {}){
                Icon(painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Account Icon")
            }
            IconButton(onClick = {}){
                Icon(painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "Account Icon")
            }
        }*/
    )
}