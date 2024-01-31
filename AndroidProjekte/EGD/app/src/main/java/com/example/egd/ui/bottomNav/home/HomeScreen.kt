package com.example.egd.ui

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.egd.R
import com.example.egd.data.entities.Car
import com.example.egd.ui.bottomNav.home.NoCarsIcon
import com.example.egd.ui.dialogues.AddCostsDialogue
import com.example.egd.ui.navigation.BluetoothIconCar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier,
    goToMap: () -> Unit,
    goToEditScreen: () -> Unit,
    goToProfile: () -> Unit,
    sharedPreference: () -> SharedPreferences?,
    startForeground: () -> Unit,
    stopForegroundService: () -> Unit,
    onNoInternetConnection: () -> Boolean,
    context: Context,
    goToStatisticsScreen: () -> Unit
){
    val homeUiState = viewModel.homeUiState.collectAsState().value
    var tmpBool = false

    val scrollState = rememberScrollState()
    var searchBarContent = homeUiState.searchBarContent
    //viewModel.readCarsFromJson(LocalContext.current.resources.openRawResource(R.raw.car)

    var listCars = homeUiState.cars

    val connectionState = viewModel.connectionState.collectAsState().value

    if (homeUiState.user?.id != null){
        viewModel.getCarsForUserId(homeUiState.user.id)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    //if (connectionState.connectionState == ConnectionEnum.Connected){
    //    AccidentDialogue(viewModel = viewModel)
    //}


    DisposableEffect(
        key1 = lifecycleOwner
    ) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (homeUiState.user?.id != null) {
                    viewModel.getCarsForUserId(homeUiState.user.id)

                    viewModel.startCarTrackingService()
                    viewModel.startBLEService()
                } else {
                    viewModel.getUserForEmail(sharedPreference)
                    viewModel.startCarTrackingService()
                    viewModel.startBLEService()
                }
            }
            if (event == Lifecycle.Event.ON_CREATE) {
                if (homeUiState.user?.id != null) {
                    viewModel.getCarsForUserId(homeUiState.user.id)
                    viewModel.startCarTrackingService()
                    viewModel.startBLEService()
                    viewModel.startInvitationService(onNoInternetConnection)
                } else {
                    viewModel.getUserForEmail(sharedPreference)
                    viewModel.startCarTrackingService()
                    viewModel.startBLEService()
                    viewModel.startInvitationService(onNoInternetConnection)
                }
            }
            /*if (event == Lifecycle.Event.ON_START) {
                viewModel.getUserForEmail(sharedPreference)
            }*/
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Column (
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SearchBarHome(searchBarContent = searchBarContent, viewModel, goToProfile)

        Spacer(modifier = Modifier.height(20.dp))
        if (listCars != null && listCars.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {
                for (car in listCars) {

                    CarCard(
                        car = car,
                        name = car.name,
                        car.latitude,
                        car.longitude,
                        viewModel,
                        { goToMap() },
                        { goToEditScreen() },
                        context,
                        car.isAdmin,
                        goToStatisticsScreen
                    )
                }
            }
        }else if (listCars != null && listCars.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 30.dp), verticalArrangement = Arrangement.Center
            ) {
                NoCarsIcon()
            }
        }
    }
}

@Composable
fun SearchBarHome(searchBarContent: String, viewModel: EGDViewModel, goToProfile: () -> Unit){

    OutlinedTextField(
        value = searchBarContent,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {}),
        onValueChange = {viewModel.setHomeSearchBarContent(it)},
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search Bar") },
        trailingIcon = {
            IconButton(onClick = { goToProfile() }){
                BadgedBox(badge = { Badge(backgroundColor = MaterialTheme.colors.secondary, modifier = Modifier.offset(y=10.dp,x=(-3).dp)) { Text(viewModel.getNotificationCount().toString(), color = Color.Black) } }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                        contentDescription = "Person Icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = MaterialTheme.colors.background,
            unfocusedBorderColor = MaterialTheme.colors.background
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(20.dp))
    )
}

@Composable
fun CarCard(
    car: Car,
    name: String,
    latitude: Double,
    longitude: Double,
    viewModel: EGDViewModel,
    goToMap: () -> Unit,
    goToEditScreen: () -> Unit,
    context: Context,
    isAdmin: Boolean?,
    goToStatisticsScreen: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                goToStatisticsScreen()
                viewModel.setStatisticsCar(car)
                viewModel.getDrivesByUserCar2()
            },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {

        Column(modifier = Modifier.fillMaxWidth()){
            Row(
                modifier = Modifier
                    .padding(start = 17.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = name, fontWeight = MaterialTheme.typography.body2.fontWeight)

                IconButton(onClick =
                {
                    viewModel.setCar(car, car.consumption.toString())
                    goToEditScreen()
                }
                ) {
                    if (isAdmin == true){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                            contentDescription = "edit Icon",
                        )
                    }
                    else{
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_info_24),
                            contentDescription = "Info Icon",
                        )
                    }
                }
            }
            Divider(color = Color.LightGray)
            Row(modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Button(
                    onClick = {
                        var location = Location("")
                        location.latitude = latitude
                        location.longitude = longitude
                        viewModel.setCurrentLocation( location)
                        goToMap()
                              },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background, contentColor = MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.shadow(0.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    shape= MaterialTheme.shapes.large

                ) {
                    Text(text="Go to map",color = MaterialTheme.colors.primaryVariant, fontWeight = MaterialTheme.typography.body2.fontWeight)
                }
                BluetoothIconCar(viewModel = viewModel, car = car)
                Box(){
                    IconButton(onClick = { expanded = !expanded }, enabled = isAdmin ?: false) {
                        Icon(painterResource(id = R.drawable.ic_baseline_more_vert_24), contentDescription = "More Vert Icon")
                    }

                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(3.dp))) {
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(color = Color.White)) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    viewModel.deleteCar(car, context)
                                },
                                enabled = true,
                                modifier = Modifier.background(color = Color.White)
                            ) {
                                Text(color = MaterialTheme.colors.primary, text = "Delete")
                            }
                        }
                    }
                }
                
            }
        }

        /*Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }*/

    }
}



