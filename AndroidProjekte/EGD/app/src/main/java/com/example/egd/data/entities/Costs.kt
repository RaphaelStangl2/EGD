package com.example.egd.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Costs(
    @SerializedName("id")
    @Expose
    var id: Long?,
    @SerializedName("description")
    @Expose
    var description:String = "",
    @SerializedName("costs")
    @Expose
    var costs: Double = 0.00,
    @SerializedName("userCar")
    @Expose
    var userCar: UserCar? = null,
)
