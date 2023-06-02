package com.example.egd.ui.getStarted

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.permissions.PermissionUtils
import com.example.egd.ui.validation.ErrorText
import com.example.egd.ui.validation.ValidationService
import com.google.accompanist.permissions.*

@ExperimentalPermissionsApi
@Composable
fun ConnectScreen(viewModel:EGDViewModel, showBluetoothDialogue:()->Unit, validationService: ValidationService, triedToSubmit:Boolean
) {

    val viewModelValue = viewModel.getStartedUiState.collectAsState().value

    var doNotShowRationale = viewModelValue.doNotShowRational
    val bluetoothPermission = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)

    val validationConnection = validationService.validateConnectionScreen(viewModelValue.connectionSuccessful)

    PermissionsRequired(
        multiplePermissionsState = bluetoothPermission,
        permissionsNotGrantedContent = { if (doNotShowRationale) {
            Text("Feature not available")
        } else {
            Column {
                Text("Bluetooth has to activated for you to connect to your EGD-Device. Please grant the permission.")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = { bluetoothPermission.launchMultiplePermissionRequest() }) {
                        Text("OK")
                    }
                }
            }
        } },
        permissionsNotAvailableContent = { Column {
            Text(
                "Bluetooth permission denied. See this FAQ with information about why we " +
                        "need this permission. Please, grant us access on the Settings screen."
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {}) {
                Text("Open Settings")
            }
        } }) {

        showBluetoothDialogue()

        Row(){
            Text(text="Connect to your EGD Device")
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)){

            Button(onClick = {
                             viewModel.initializeConnection { /*TODO*/ }
            }, modifier = Modifier.size(150.dp, 150.dp), shape = RoundedCornerShape(75.dp)){
                Text(text="CONNECT")
            }
        }
    }

    Row(){
        if (!validationConnection.valid && triedToSubmit){
            ErrorText(message = validationConnection.message)
        }
    }

    /*PermissionRequired(
        permissionState = bluetoothPermission,
        permissionNotGrantedContent = {
            if (doNotShowRationale) {
                Text("Feature not available")
            } else {
                Column {
                    Text("The camera is important for this app. Please grant the permission.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { bluetoothPermission.launchPermissionRequest() }) {
                            Text("OK")
                        }
                    }
                }
            }
        },
        permissionNotAvailableContent = {
            Column {
                Text(
                    "Camera permission denied. See this FAQ with information about why we " +
                            "need this permission. Please, grant us access on the Settings screen."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}) {
                    Text("Open Settings")
                }
            }
        }
    ) {

    }*/
}