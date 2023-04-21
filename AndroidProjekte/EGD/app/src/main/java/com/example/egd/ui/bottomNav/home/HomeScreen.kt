package com.example.egd.ui

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.ui.platform.LocalContext

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
import kotlinx.coroutines.flow.update



@Composable
fun HomeScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier,
    goToMap: () -> Unit,
    goToEditScreen: () -> Unit,
    goToProfile: () -> Unit
){
    val homeUiState = viewModel.homeUiState.collectAsState().value

    val scrollState = rememberScrollState()
    var searchBarContent = homeUiState.searchBarContent
    //viewModel.readCarsFromJson(LocalContext.current.resources.openRawResource(R.raw.car))

    var listCars = homeUiState.cars

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_RESUME){
                    viewModel.getCarsForUserId(1)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )


    Column (
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SearchBarHome(searchBarContent = searchBarContent, viewModel, goToProfile)

        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)){
            if (listCars != null) {
                for(car in listCars) {
                    CarCard(car = car,name = car.name, car.latitude, car.longitude, viewModel, {goToMap()}, {goToEditScreen()})
                }
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
            .shadow(6.dp, shape = RoundedCornerShape(20.dp))
    )
}

@Composable
fun CarCard(car: Car, name: String, latitude: Double, longitude: Double, viewModel: EGDViewModel, goToMap: () -> Unit, goToEditScreen: () -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
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
                    viewModel.setCar(car)
                    goToEditScreen()

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                        contentDescription = "edit Icon",
                    )
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

                Box(){
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(painterResource(id = R.drawable.ic_baseline_more_vert_24), contentDescription = "More Vert Icon")
                    }

                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(3.dp))) {
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(color = Color.White)) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                },
                                enabled = false,
                                modifier = Modifier.background(color = Color.White)
                            ) {
                                Text(text = "Delete")
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



