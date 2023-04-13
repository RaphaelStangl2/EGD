package com.example.egd.data

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val response:String = ""
)