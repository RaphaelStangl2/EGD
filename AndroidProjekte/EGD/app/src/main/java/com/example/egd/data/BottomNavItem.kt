package com.example.egd.data

import com.example.egd.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24, "home")
    object Map: BottomNavItem("Map", R.drawable.ic_baseline_map_24, "map")
    object Statistics: BottomNavItem("Statistics", R.drawable.ic_baseline_statistics_24, "statistics")
}
