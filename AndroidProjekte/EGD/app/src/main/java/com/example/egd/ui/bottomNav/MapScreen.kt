package com.example.egd.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import android.util.Log.ERROR
import android.util.Log.INFO
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.egd.R
import com.example.egd.ui.permissions.PermissionUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier,
    onGpsRequired: () -> Unit,
){

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val mapUiState = viewModel.mapUiState.collectAsState().value
    var searchBarContent = mapUiState.searchBarContent
    var startLocation:Location? = mapUiState.startLocation
    val cars = viewModel.homeUiState.collectAsState().value.cars


    //var cameraPositionState: CameraPositionState? = null
    //cameraPositionState = rememberCameraPositionState()
    val cameraPositionState = rememberCameraPositionState {}

    getUserPosition(viewModel) { location ->
        try{
            if (startLocation == null && location == null){
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(
                    cars?.get(0)?.latitude ?: 0.0, cars?.get(0)?.longitude ?:0.0), 12f)))
            }
            else if (startLocation == null){
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(location?.latitude ?: 0.0, location?.longitude ?:0.0), 12f)))
            }else if (startLocation.latitude != 1.0){
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(LatLng(startLocation.latitude, startLocation.longitude), 12f)))
            }
        } catch(e:Exception){
        }
    }


    val mapPermission = rememberMultiplePermissionsState(permissions = PermissionUtils.mapsPermissions)
    var doNotShowRationale = mapUiState.doNotShowRational
    val lifecycleOwner = LocalLifecycleOwner.current

    PermissionsRequired(
        multiplePermissionsState = mapPermission,
        permissionsNotGrantedContent = { if (doNotShowRationale) {
            Text("Feature not available")
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Text("GPS Permissions have to be activated for you to see your location on the map. Please grant the permission.")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = { mapPermission.launchMultiplePermissionRequest() }) {
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

        Column(modifier = modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){


            Box(){

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState=cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true),
                    uiSettings = MapUiSettings(myLocationButtonEnabled = false)
                ){
                    var bitmap: BitmapDescriptor? = null
                    if (cars != null) {
                        for (car in cars){
                            Marker(state = MarkerState(position = LatLng(car.latitude, car.longitude)),
                                icon = bitmapDescriptorFromVector(
                                    LocalContext.current, R.drawable.ic_baseline_directions_car_24
                                )
                            )
                            /*MapMarker(
                                position = LatLng(car.latitude, car.longitude),
                                title = "Your Title 2",
                                context = LocalContext.current,
                                iconResourceId = R.drawable.car_pin
                            )*/
                        }
                    }
                }

                /*GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                )*/
                OutlinedTextField(
                    value = searchBarContent,
                    onValueChange = {viewModel.setMapSearchBarContent(it)},
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
}

@SuppressLint("MissingPermission")
fun getUserPosition(viewModel:EGDViewModel): Location? {

    val coroutineScope = CoroutineScope(Dispatchers.Default)
    var locationResult: Location? = null

    val job = coroutineScope.launch {
    locationResult = viewModel.fusedLocationClient.lastLocation.await()
    }

    return locationResult
}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(position = position),
        title = title,
        icon = icon,
    )
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@SuppressLint("MissingPermission")
fun getUserPosition(viewModel:EGDViewModel, callback: (Location?) -> Unit) {
    viewModel.fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        callback(location)
    }.addOnFailureListener { exception ->
        // Handle failure
        callback(null)
    }
}


