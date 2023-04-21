package com.example.egd.data.entities

import com.google.gson.annotations.Expose

data class User(
    @Expose
    val id : Long?,
    @Expose
    val userName: String,
    @Expose
    val email: String,
    @Expose
    val password: String
    )
