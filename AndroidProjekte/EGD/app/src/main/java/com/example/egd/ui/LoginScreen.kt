package com.example.egd.ui

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.egd.R
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@Composable
fun LoginScreen(
    viewModel: EGDViewModel,
    modifier: Modifier,
    onLogin: () -> Unit,
    sharedPreference: () -> SharedPreferences?
){
    val uiState = viewModel.loginUiState.collectAsState().value

    var validationService = ValidationService()

    var passwordVisibility = uiState.passwordVisibility
    var email = uiState.email
    var password = uiState.password
    var response = uiState.response
    var isTriedToSubmit = uiState.triedToSubmit

    var emailValidationObject = validationService.validateEmail(email)
    var passwordValidationObject = validationService.validatePassword(password)

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
            Text(text = response, color = MaterialTheme.colors.error)
        }
        Row(){
            OutlinedTextField(
                value = email,
                onValueChange = {viewModel.setEmailLogin(it)},
                placeholder = { Text(text = "Email")},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                isError = !emailValidationObject.valid && isTriedToSubmit
            )
        }
        Row(){
            if (!emailValidationObject.valid && isTriedToSubmit){
                ErrorText(message = emailValidationObject.message)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            OutlinedTextField(
                value = password,
                onValueChange = {viewModel.setPasswordLogin(it)},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
                else PasswordVisualTransformation(),
                isError = !passwordValidationObject.valid && isTriedToSubmit
            )

        }
        Row(){
            if (!passwordValidationObject.valid && isTriedToSubmit){
                ErrorText(message = passwordValidationObject.message)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            Button(

                onClick = {
                    if (validationService.validateLoginForm(email, password)){
                        viewModel.checkLogin(onLogin, sharedPreference)
                        viewModel.getUserForEmail { sharedPreference() }
                    } else{
                        viewModel.setTriedToSubmitLogin(true)
                    }
                          },
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
