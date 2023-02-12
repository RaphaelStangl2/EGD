package com.example.egd.ui.internet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.egd.R
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.data.StartItem


@Composable
fun NoInternetScreen(checkConnection: () -> Boolean, onConnection: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Row(){
                Text(
                    text = "No Internet Connection",
                    fontSize = 23.sp,
                    color = Color.White
                )
            }
            Row(){
                Icon(painterResource(id = R.drawable.ic_baseline_signal_wifi_connected_no_internet_4_24), contentDescription = "no Connection Icon", tint = Color.White, modifier = Modifier.size(80.dp, 80.dp))

            }
            Row(){
                //GetStartedButton({}, "Reload")

                Button(
                    onClick = {
                              if (checkConnection())
                                  onConnection()

                    },
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    )
                ){
                    Text(text = "Reload", fontWeight = FontWeight.Bold)

                }
            }


        }
    }
}


