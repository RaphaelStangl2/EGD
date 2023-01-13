package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.egd.R

@Composable
fun GetStarted(viewModel: EGDViewModel, onRegistered: () -> Unit, modifier: Modifier) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val progressBarWidth = screenWidth - (screenWidth.times(0.12F))

    val uiState = viewModel.getStartedUiState.collectAsState().value
    val loginUiState = viewModel.loginUiState.collectAsState().value

    var step = uiState.step
    var numberOfSteps = uiState.numberOfSteps
    var egdDevice = uiState.EGDDevice
    var carName = uiState.carName
    var fuelConsumption = uiState.averageCarConsumption
    var firstName = uiState.firstName
    var email = uiState.email
    var password = uiState.password

    var passwordVisibility = loginUiState.passwordVisibility



    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_baseline_visibility_24)
    else
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)



    Column(
        modifier = modifier
            .padding(screenWidth.times(0.06F))
            .fillMaxWidth()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Row() {
            ProgressBar(progress = step, numberOfSteps = numberOfSteps, progressBarWidth)
        }

        if (step == 1) {
            DeviceSelectionFields(viewModel, egdDevice)
        } else if (numberOfSteps == 5) {
            if (step == 2){
                ConnectScreen()
            }
            else if(step == 3) {
                CarInfoScreen(carName, fuelConsumption, viewModel)
            }
            else if(step == 5) {
                RegisterScreen(viewModel = viewModel, userName = firstName, email = email, password = password, passwordVisibility = passwordVisibility, icon = icon)
            }


        } else if (numberOfSteps == 3) {
            if (step == 3){
                RegisterScreen(viewModel = viewModel, userName = firstName, email = email, password = password, passwordVisibility = passwordVisibility, icon = icon)
            }
        }


            Row(verticalAlignment = Alignment.Bottom) {
                Button(
                    onClick = {
                        if (step + 1 > numberOfSteps){
                            onRegistered()
                        }else{
                            viewModel.setStep(step + 1)
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "NEXT")
                }
            }

        }

    }



    @Composable
    fun DeviceSelectionFields(viewModel: EGDViewModel, hasEgdDevice: Boolean) {
        Row() {
            Text(text = "Do you have a EGD Device?")

        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = hasEgdDevice,
                onClick = {

                    if (!hasEgdDevice) {
                        viewModel.setEGDDevice(!hasEgdDevice)
                    }
                    viewModel.setNumberOfSteps(5)
                },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
            )
            Text(text = "Yes")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !hasEgdDevice,
                onClick = {
                    if (hasEgdDevice){
                        viewModel.setEGDDevice(!hasEgdDevice)
                    }
                    viewModel.setNumberOfSteps(3)
                },
                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primaryVariant)
            )
            Text(text = "No")
        }
    }

@Composable
fun ConnectScreen() {
    Row(){
        Text(text="Connect to your EGD Device")
    }
    Row(){
        Button(onClick = {}){
            Text(text="CONNECT")
        }
    }
}

@Composable
fun CarInfoScreen(carName:String, fuelConsumption: String, viewModel: EGDViewModel) {
    Row(){
        Text(text="Car Name:")
    }
    Row(){
        TextField(
            value = carName,
            onValueChange = {viewModel.setCarName(it)},
            placeholder = {Text(text="My Car")},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
        )
    }
    Spacer(modifier = Modifier.height(7.dp))

    Row(){
        Text(text="Average fuel consumption per 100 km:")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            value = fuelConsumption.toString(),
            onValueChange = {viewModel.setFuelConsumption(it)},
            placeholder = {Text(text= "7")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier.width(77.dp)
        )
        Text(text="liters")
    }
}


@Composable
fun RegisterScreen(viewModel: EGDViewModel, userName:String, email: String, password: String, passwordVisibility: Boolean, icon: Painter) {

    Row(){
        TextField(
            value = userName,
            onValueChange = {viewModel.setFirstName(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = {Text(text="User Name")}
        )
    }
    Spacer(modifier = Modifier.height(7.dp))


    Row(){
        TextField(
            value = email,
            onValueChange = {viewModel.setEmailRegister(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = {Text(text="Email")}

        )
    }
    Spacer(modifier = Modifier.height(7.dp))


    Row(){
        TextField(
            value = password,
            onValueChange = {viewModel.setPasswordRegister(it)},
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = {Text(text = "Password")},
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
