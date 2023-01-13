package com.example.egd.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.egd.R
import kotlinx.coroutines.launch



@Composable
fun MapScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier
){
    val mapUiState = viewModel.mapUiState.collectAsState().value
    
    var searchBarContent = mapUiState.searchBarContent
    

    val currentLocation = LatLng(1.35, 103.87)


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 16f)
    }


    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(){

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            )
            OutlinedTextField(
                value = searchBarContent,
                onValueChange = {viewModel.setSearchBarContent(it)},
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search Bar")},
                trailingIcon = {
                    IconButton(onClick = {}){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                            contentDescription = "Person Icon",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },               
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    focusedBorderColor = MaterialTheme.colors.background,
                    unfocusedBorderColor = MaterialTheme.colors.background

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(20.dp))
            )
        }
    }
}