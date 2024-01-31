package com.example.egd.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class Drive(
    @SerializedName("id")
    @Expose
    var id: Long?,
    @SerializedName("kilometers")
    @Expose
    var kilometers: Double?,
    @SerializedName("userCar")
    @Expose
    var userCar: UserCar?,
    @SerializedName("date")
    @Expose
    var date: LocalDate?,
)
