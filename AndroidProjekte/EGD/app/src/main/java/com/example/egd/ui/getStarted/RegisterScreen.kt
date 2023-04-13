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

@Composable
fun RegisterScreen(viewModel: EGDViewModel, userName:String, email: String, password: String,response:String, passwordVisibility: Boolean, icon: Painter) {

    Row(){
        Text(text = response, color = Color.Red)
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row(){
        TextField(
            value = userName,
            onValueChange = {viewModel.setFirstName(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text="User Name") }
        )
    }


    Row(){
        TextField(
            value = email,
            onValueChange = {viewModel.setEmailRegister(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text(text="Email") }

        )
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
            else PasswordVisualTransformation()
        )
    }


    Row(){
        Text(text = "at least 8 symbols", fontStyle = FontStyle.Italic)
    }
    Spacer(modifier = Modifier.height(10.dp))

}