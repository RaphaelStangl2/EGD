package com.example.egd.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.egd.R
import com.example.egd.ui.EGDViewModel

@Composable
fun TopAppBarBackButton(
    navController: NavHostController,
    title: @Composable () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(onClick = {onBackButtonClick()}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Back Arrow"
                )
            }
        }
    )
}
