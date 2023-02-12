package com.example.egd

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.egd.data.BottomNavItem
import com.example.egd.data.StartItem
import com.example.egd.ui.*
import com.example.egd.ui.getStarted.GetStarted
import com.example.egd.ui.internet.NoInternetScreen
import com.example.egd.ui.permissions.SystemBroadcastReceiver

/*@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation

){

}*/

@Composable
fun TopAppBarProfile(viewModel: EGDViewModel){
    TopAppBar(
        title = {Text("")},
        /*actions = {
            //SearchBarHome(searchBarContent = "", viewModel = viewModel)
            IconButton(onClick = {}){
                Icon(painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "Account Icon")
            }
            IconButton(onClick = {}){
                Icon(painterResource(id = R.drawable.ic_baseline_person_24), contentDescription = "Account Icon")
            }
        }*/
    )
}

@Composable
fun TopAppBarBackButton(
    navController: NavHostController,
    title: @Composable () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(onClick = {onBackButtonClick()}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Back Arrow"
                )
            }
        }
    )
}

@Composable
fun BottomAppBar(navController: NavHostController,
                 modifier: Modifier = Modifier){

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Statistics,
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier,
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, tint = Color.White) },
                label = { Text(text = item.title,
                    fontSize = 15.sp,
                    color = Color.White
                    ) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun EGDApp(modifier: Modifier = Modifier,
           viewModel: EGDViewModel = viewModel(),
           onBluetoothStateChanged:()->Unit,
           onNoInternetConnection:()->Boolean
){

    val navController = rememberNavController()

    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?: return@SystemBroadcastReceiver
        if(action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    /*Scaffold(

        bottomBar = {
            BottomAppBar(navController)
        }


    ) { innerPadding ->*/
        val loginUiState by viewModel.loginUiState.collectAsState()
        val getStartedUiState by viewModel.getStartedUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination =  if(onNoInternetConnection())
                                    StartItem.StartScreen.screen_route
                                else
                                    StartItem.NoConnectionScreen.screen_route,
            //modifier = modifier.padding(innerPadding)
        ){
            composable(route = StartItem.StartScreen.screen_route){
                    StartScreen(
                        onGetStartedButtonClicked = {
                            navController.navigate(StartItem.GetStartedScreen.screen_route)
                        },
                        onLoginButtonClicked = {
                            navController.navigate(StartItem.LoginScreen.screen_route)
                        })
                }



            composable(route = StartItem.NoConnectionScreen.screen_route){
                NoInternetScreen({onNoInternetConnection()}) { navController.navigate(StartItem.StartScreen.screen_route) }
            }
            composable(route = StartItem.LoginScreen.screen_route) {
                Scaffold(
                    topBar = { TopAppBarBackButton(navController, {Text("Sign In")}, onBackButtonClick = {navController.navigateUp()}) }
                ) { innerPadding ->
                    LoginScreen(viewModel, modifier = Modifier.padding(innerPadding))
                }
            }

            composable(route = StartItem.GetStartedScreen.screen_route){
                Scaffold(
                    topBar = { TopAppBarBackButton(navController, {Text("Get Started")}, onBackButtonClick = {
                        if ( getStartedUiState.step == 1 ){
                            navController.navigateUp()
                        }
                        else{
                            viewModel.setStep(getStartedUiState.step-1)
                        }
                    })
                    })
                { innerPadding ->
                    GetStarted(viewModel, { navController.navigate(BottomNavItem.Home.screen_route){ popUpTo(0) } },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

            composable(route = BottomNavItem.Home.screen_route) {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(navController)
                    },
                    topBar = {
                        TopAppBarProfile(viewModel)
                    }
                ) { innerPadding ->
                    HomeScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
            composable(route = BottomNavItem.Map.screen_route) {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(navController)
                    }
                ) { innerPadding ->
                    MapScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
            composable(route = BottomNavItem.Statistics.screen_route) {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(navController)
                    }
                ) { innerPadding ->

                    StatisticsScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

        }
    //}

}
