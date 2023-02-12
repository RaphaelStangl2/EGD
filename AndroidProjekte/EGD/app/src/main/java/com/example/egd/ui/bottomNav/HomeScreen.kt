package com.example.egd.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*

import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.egd.R
import com.google.android.gms.common.internal.ResourceUtils
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    viewModel: EGDViewModel,
    modifier: Modifier = Modifier,
){
    val homeUiState = viewModel.homeUiState.collectAsState().value

    val scrollState = rememberScrollState()
    var searchBarContent = homeUiState.searchBarContent
    viewModel.readCarsFromJson(LocalContext.current.resources.openRawResource(R.raw.car))

    var listCars = homeUiState.cars


    Column (
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        SearchBarHome(searchBarContent = searchBarContent, viewModel)

        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)){
            if (listCars != null) {
                for(car in listCars) {
                    CarCard(name = car.name)
                }
            }
        }
    }



}

@Composable
fun SearchBarHome(searchBarContent: String, viewModel: EGDViewModel){

    OutlinedTextField(
        value = searchBarContent,
        onValueChange = {viewModel.setHomeSearchBarContent(it)},
        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Search Bar") },
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
            .shadow(6.dp, shape = RoundedCornerShape(20.dp))
    )
}

@Composable
fun CarCard(name: String) {
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
                    .padding(start = 17.dp, top=10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = name, fontWeight = MaterialTheme.typography.body2.fontWeight)

                IconButton(onClick = {}) {
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
                    onClick = {},
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painterResource(id = R.drawable.ic_baseline_more_vert_24), contentDescription = "More Vert Icon")
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

