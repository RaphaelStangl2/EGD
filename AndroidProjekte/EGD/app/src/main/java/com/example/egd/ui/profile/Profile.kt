package com.example.egd.ui.profile

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Profile(logout: () -> Unit, modifier: Modifier){
    Button(onClick = { logout() }) {
        Text("Log Out")
    }
}