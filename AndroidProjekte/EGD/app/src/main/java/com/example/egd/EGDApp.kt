package com.example.egd

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
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
import com.example.egd.ui.EGDViewModel
import com.example.egd.ui.HomeScreen
import com.example.egd.ui.MapScreen
import com.example.egd.ui.StatisticsScreen
import java.util.Vector

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
           onBluetoothStateChanged:()->Unit
){

    val navController = rememberNavController()

    Scaffold(
        /*topBar = {
            CupcakeAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }*/
        bottomBar = {
            BottomAppBar(navController)
        }
        

    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.screen_route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = BottomNavItem.Home.screen_route) {
                HomeScreen(viewModel = viewModel)
            }
            composable(route = BottomNavItem.Map.screen_route) {
                MapScreen(viewModel = viewModel)
            }
            composable(route = BottomNavItem.Statistics.screen_route) {
                StatisticsScreen(viewModel = viewModel)
            }

        }
    }

}
