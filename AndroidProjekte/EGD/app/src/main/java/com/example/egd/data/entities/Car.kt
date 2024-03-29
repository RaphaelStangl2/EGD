package com.example.egd.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id")
    @Expose
    var id:Long?,
    @SerializedName("name")
    @Expose
    var name:String,
    @SerializedName("consumption")
    @Expose
    var consumption:Double,
    @SerializedName("latitude")
    @Expose
    var latitude:Double,
    @SerializedName("longitude")
    @Expose
    var longitude:Double,
    @SerializedName("uuid")
    @Expose
    var uuid:String?,
    @SerializedName("licensePlate")
    @Expose
    var licensePlate: String,
    @SerializedName("currentUser")
    @Expose
    var currentUser: User?,
    var isAdmin: Boolean?,
    )
