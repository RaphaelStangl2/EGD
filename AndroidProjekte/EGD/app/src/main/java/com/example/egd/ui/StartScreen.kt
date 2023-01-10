package com.example.egd.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.R

@Composable
fun StartScreen(
    onGetStartedButtonClicked: () -> Unit = {},
    onLoginButtonClicked: () -> Unit = {}
){
    val textColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ){
        /*Image(
            painter = painterResource(id = R.drawable.peach_background),
            contentDescription = "wave Background",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()

        )*/
        Column(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){

            Spacer(modifier = Modifier.height(150.dp))

            Row(){

                Text(
                    text = "EGD",
                    fontSize = 50.sp,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.height(300.dp))

            Spacer(modifier = Modifier.height(35.dp))
            Row(){
                Button(onClick = {onGetStartedButtonClicked()}, modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = MaterialTheme.colors.primary ),
                    elevation = elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp)
                ){
                    Text(text= "Get started", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row() {

                Button( onClick = {onLoginButtonClicked()}, modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    elevation = elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp)
                ){
                    Text(text = "Log In", fontWeight = FontWeight.Bold)

                }
            }
        }
    }
}