package com.example.bletest.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bletest.ui.theme.BLETestTheme

@Composable
fun ConnectScreen(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BLETestTheme {
        ConnectScreen("Android")
    }
}