package com.example.egd.data

sealed class StartItem (var screen_route:String){
    object StartScreen :StartItem("start")
    object LoginScreen :StartItem("login")
    object GetStartedScreen :StartItem("getStarted")
    object NoConnectionScreen: StartItem("noConnection")

}
