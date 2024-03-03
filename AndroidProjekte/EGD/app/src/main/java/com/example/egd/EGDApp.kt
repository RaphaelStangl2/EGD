package com.example.egd

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.egd.data.BottomNavItem
import com.example.egd.data.StartItem
import com.example.egd.ui.*
import com.example.egd.ui.bottomNav.home.AddCarDialogue
import com.example.egd.ui.bottomNav.home.CarEditScreen
import com.example.egd.ui.dialogues.AccidentDialogue
import com.example.egd.ui.getStarted.AddUserScreen
import com.example.egd.ui.getStarted.GetStarted
import com.example.egd.ui.internet.NoInternetScreen
import com.example.egd.ui.permissions.PermissionUtils
import com.example.egd.ui.permissions.SystemBroadcastReceiver
import com.google.accompanist.permissions.*
import com.example.egd.ui.navigation.*
import com.example.egd.ui.profile.Profile
import com.google.accompanist.pager.ExperimentalPagerApi

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






@OptIn(ExperimentalPermissionsApi::class, ExperimentalPagerApi::class)
@Composable
fun EGDApp(
    modifier: Modifier = Modifier,
    viewModel: EGDViewModel = viewModel(),
    onBluetoothStateChanged: () -> Unit,
    onGPSRequired: () -> Unit,
    onNoInternetConnection: () -> Boolean,
    sharedPreference: () -> SharedPreferences?,
    startForegroundService: () -> Unit,
    context: Context,
    stopForegroundService: () -> Unit
) {

    val navController = rememberNavController()
    val bluetoothPermission =
        rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)

    PermissionsRequired(
        multiplePermissionsState = bluetoothPermission,
        permissionsNotGrantedContent = { /*TODO*/ },
        permissionsNotAvailableContent = { /*TODO*/ }) {
        SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED) { bluetoothState ->
            val action = bluetoothState?.action ?: return@SystemBroadcastReceiver
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                onBluetoothStateChanged()
            }
        }
    }

    /*Scaffold(

        bottomBar = {
            BottomAppBar(navController)
        }


    ) { innerPadding ->*/
    val loginUiState by viewModel.loginUiState.collectAsState()
    val getStartedUiState by viewModel.getStartedUiState.collectAsState()
    val editCarUiState by viewModel.editCarUiState.collectAsState()
    val homeUiState by viewModel.homeUiState.collectAsState()


    if (getStartedUiState.connectionSuccessful && getStartedUiState.accidentCode == "1") {
        AccidentDialogue(viewModel)
    }

    NavHost(
        navController = navController,
        startDestination = if (!onNoInternetConnection())
            StartItem.NoConnectionScreen.screen_route
        else if (viewModel.checkLogin(sharedPreference))
            BottomNavItem.Home.screen_route
        else
            StartItem.StartScreen.screen_route,
        //modifier = modifier.padding(innerPadding)
    ) {

        //Profile
        composable(route = StartItem.ProfileScreen.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(
                        navController,
                        { Text("Profile") },
                        onBackButtonClick = { navController.navigateUp() })
                }
            ) { innerPadding ->
                Profile(modifier = Modifier.padding(innerPadding), logout =
                {
                    viewModel.logout(sharedPreference, stopForegroundService)
                    navController.navigate(StartItem.StartScreen.screen_route) { popUpTo(0) }
                }, viewModel = viewModel)
            }
        }

        //Startscreen
        composable(route = StartItem.StartScreen.screen_route) {
            StartScreen(
                onGetStartedButtonClicked = {
                    navController.navigate(StartItem.GetStartedScreen.screen_route)
                },
                onLoginButtonClicked = {
                    navController.navigate(StartItem.LoginScreen.screen_route)
                }, viewModel = viewModel
            )
        }
        //NoConnectionScreen
        composable(route = StartItem.NoConnectionScreen.screen_route) {
            NoInternetScreen({ onNoInternetConnection() })
            {
                if (!navController.navigateUp()) {
                    navController.navigate(StartItem.StartScreen.screen_route)
                }
            }
        }
        //LoginScreen
        composable(route = StartItem.LoginScreen.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(
                        navController,
                        { Text("Sign In") },
                        onBackButtonClick = { navController.navigateUp() })
                }
            ) { innerPadding ->
                LoginScreen(
                    viewModel,
                    modifier = Modifier.padding(innerPadding),
                    { navController.navigate(BottomNavItem.Home.screen_route) { popUpTo(0) } },
                    sharedPreference
                )
            }
        }
        //GetStartedScreen
        composable(route = StartItem.GetStartedScreen.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(
                        navController,
                        { Text("Get Started") },
                        onBackButtonClick = {
                            if (getStartedUiState.step == 1) {
                                navController.navigateUp()
                                viewModel.closeConnection()
                            } else if (getStartedUiState.step == 2){
                                viewModel.closeConnection()
                                viewModel.setStep(getStartedUiState.step - 1)
                            } else {
                                viewModel.setStep(getStartedUiState.step - 1)
                            }
                        })
                })
            { innerPadding ->
                GetStarted(viewModel,
                    { navController.navigate(BottomNavItem.Home.screen_route) { popUpTo(0) } },
                    onBluetoothStateChanged = { onBluetoothStateChanged() },
                    modifier = Modifier.padding(innerPadding),
                    sharedPreference = { sharedPreference() }
                )
            }
        }
        //AddCarScreen
        composable(route = BottomNavItem.AddCarScreen.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(navController, { Text("Add Car") }, onBackButtonClick = {
                        if (getStartedUiState.step == 1) {
                            navController.navigateUp()
                        } else {
                            viewModel.setStep(getStartedUiState.step - 1)
                        }

                    })
                })
            { innerPadding ->
                AddCarDialogue(
                    viewModel,
                    { navController.navigate(BottomNavItem.Home.screen_route) { popUpTo(0) } },
                    onBluetoothStateChanged = { onBluetoothStateChanged() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        //EditCarScreen
        composable(route = BottomNavItem.EditCarScreen.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(navController, { Text("Edit Car") }, onBackButtonClick = {
                        navController.navigateUp()
                    })
                })
            { innerPadding ->


                Column() {
                    Spacer(modifier = Modifier.height(5.dp))
                    CarEditScreen(viewModel = viewModel,
                        onUpdate = {
                            navController.navigate(BottomNavItem.Home.screen_route) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.padding(innerPadding),
                        goToFriendsAddScreen = { navController.navigate(BottomNavItem.AddUserToCar.screen_route) })

                }
            }
        }

        //AddUserToCarScreen -- EditScreen Version

        composable(route = BottomNavItem.AddUserToCar.screen_route) {
            Scaffold(
                topBar = {
                    TopAppBarBackButton(navController, { Text("Add Users") }, onBackButtonClick = {

                        if (homeUiState.assignedFriendsList != null){
                            viewModel.setAddedFriendsList(homeUiState.assignedFriendsList!!)
                        }
                        viewModel.setAssignedFriendsList(emptyArray())
                        viewModel.setSearchFriendList(emptyArray())
                        navController.navigateUp()
                    })
                })
            { innerPadding ->
                val configuration = LocalConfiguration.current
                val screenWidth = configuration.screenWidthDp.dp

                Column(
                    modifier = modifier
                        .padding(screenWidth.times(0.06F))
                        .fillMaxSize()
                ) {
                    AddUserScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        friendSearchBarContent = getStartedUiState.friendSearchBarContent,
                        assignedFriendsList = homeUiState.assignedFriendsList,
                        searchedFriendsList = homeUiState.searchFriendList
                    )

                    /*if (homeUiState.assignedFriendsList != null){
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight(0.2f)){
                            Button(onClick = {
                                if (homeUiState.assignedFriendsList != null){
                                    for (user in homeUiState.assignedFriendsList!!){
                                        if (!editCarUiState.assignedFriendList!!.contains(user)){
                                            viewModel.addUserToAddedList(user)
                                            viewModel.addUserToAssignedEditList(user)
                                        }
                                        navController.navigateUp()

                                    }
                                }

                            }) {
                                Text("Hinzufügen")
                            }
                        }
                    }*/
                }
            }
        }

        //HomeScreen
        composable(route = BottomNavItem.Home.screen_route) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(navController)
                },
                topBar = {
                    TopAppBarProfile(viewModel)
                },
                floatingActionButton = {
                    FLoatingAddButton(
                        viewModel = viewModel
                    ) {
                        viewModel.setStep(1)
                        if (getStartedUiState.currentUUID != ""){
                            Toast.makeText(context, "Disconnect from current device before adding car", Toast.LENGTH_LONG).show()
                        }
                        else{
                            navController.navigate(BottomNavItem.AddCarScreen.screen_route)
                        }

                    }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) { innerPadding ->
                HomeScreen(viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                    goToEditScreen = { navController.navigate(BottomNavItem.EditCarScreen.screen_route) },
                    goToMap = { navController.navigate(BottomNavItem.Map.screen_route) },
                    goToProfile = { navController.navigate(StartItem.ProfileScreen.screen_route) },
                    sharedPreference = sharedPreference,
                    onNoInternetConnection = {onNoInternetConnection()},
                    stopForegroundService = { stopForegroundService() },
                    startForeground = { startForegroundService() },
                    context = context
                )
            }
        }
        //MapScreen
        composable(route = BottomNavItem.Map.screen_route) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(navController)
                }
            ) { innerPadding ->
                MapScreen(viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                    onNoInternetConnection = { onNoInternetConnection() },
                    onGpsRequired = { onGPSRequired() },
                    goToNoInternetConnection = { navController.navigate(StartItem.NoConnectionScreen.screen_route) })
            }
        }
        //StatisticsScreen
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
