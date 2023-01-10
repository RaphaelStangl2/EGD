package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewmodel.compose.*
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.egd.R

@Composable
fun LoginScreen(
    viewModel: EGDViewModel,
    modifier: Modifier
){
    val uiState = viewModel.loginUiState.collectAsState().value

    var passwordVisibility = uiState.passwordVisibility
    var email = uiState.email
    var password = uiState.password

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_baseline_visibility_24)
    else
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(100.dp))
        Row(){
            Text(
                text = "EGD",
                fontSize = 30.sp
                )
        }
        Spacer(modifier = Modifier.height(35.dp))

        Row(){
            OutlinedTextField(
                value = email,
                onValueChange = {viewModel.setEmail(it)},
                placeholder = { Text(text = "Email")},
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            OutlinedTextField(
                value = password,
                onValueChange = {viewModel.setPassword(it)},
                placeholder = { Text(text = "Password")},
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                trailingIcon = {
                    IconButton(onClick = {viewModel.setVisibility(!passwordVisibility)}){

                        Icon(
                            painter = icon,
                            contentDescription = "visibility Icon",
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation()
            )

        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()){
                Text(text = "Log in")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = {})
        }


    }
}

@Preview
@Composable
fun ShowLoginScreen() {
    //LoginScreen()
}
