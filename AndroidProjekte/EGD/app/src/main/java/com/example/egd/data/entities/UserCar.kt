package com.example.egd.data.entities

import com.google.gson.annotations.Expose

data class UserCar(
    @Expose
    val id:Long?,
    @Expose
    val user: User,
    @Expose
    val car: Car,
    @Expose
    val isAdmin: Boolean,
)