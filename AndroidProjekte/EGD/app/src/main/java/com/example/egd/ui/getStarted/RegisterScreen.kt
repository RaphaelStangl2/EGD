package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService

@Composable
fun RegisterScreen(viewModel: EGDViewModel, userName:String, email: String, password: String,response:String, passwordVisibility: Boolean, icon: Painter, triedToSubmit: Boolean ) {

    val validationService = ValidationService()

    var validatePassword = validationService.validatePassword(password)
    var validateEmail = validationService.validateEmail(email)
    var validateUserName = validationService.validateUserName(userName)

    Row(){
        Text(text = response, color = MaterialTheme.colors.error)
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row(){
        TextField(
            value = userName,
            onValueChange = {viewModel.setFirstName(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text="User Name") },
            isError = !validateUserName.valid && triedToSubmit
        )
    }
    Row(){
        if (!validateUserName.valid && triedToSubmit){
            ErrorText(message = validateUserName.message)
        }
    }


    Row(){
        TextField(
            value = email,
            onValueChange = {viewModel.setEmailRegister(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text="Email") },
            isError = !validateEmail.valid && triedToSubmit
        )
    }
    Row(){
        if (!validateEmail.valid && triedToSubmit){
            ErrorText(message = validateEmail.message)
        }
    }
    Spacer(modifier = Modifier.height(7.dp))


    Row(){
        TextField(
            value = password,
            onValueChange = {viewModel.setPasswordRegister(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text = "Password") },
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
            isError = !validatePassword.valid && triedToSubmit
        )
    }

    Row(){
        if (!validatePassword.valid && triedToSubmit){
            ErrorText(message = validatePassword.message)
        }
    }

    Row(){
        Text(text = "at least 8 symbols", fontStyle = FontStyle.Italic)
    }
    Spacer(modifier = Modifier.height(10.dp))

}