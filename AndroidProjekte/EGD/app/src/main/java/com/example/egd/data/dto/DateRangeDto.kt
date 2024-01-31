package com.example.egd.data.dto

import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class DateRangeDto(
    @SerializedName("carId")
    @Expose
    var carId: Long?,
    @SerializedName("fromDate")
    @Expose
    var fromDate: Date,
    @SerializedName("toDate")
    @Expose
    var toDate: Date,
)
