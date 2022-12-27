package com.example.egd.data

sealed class BottomNavItem(var title:String, var screen_route:String){
    object Home : BottomNavItem("Home", "home")
    object Map: BottomNavItem("Map", "map")
    object Statistics: BottomNavItem("Statistics", "statistics")
    object Profile: BottomNavItem("Profile", "profile")
}
