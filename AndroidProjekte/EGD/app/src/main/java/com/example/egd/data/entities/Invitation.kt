package com.example.egd.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Invitation(
    @SerializedName("id")
    @Expose
    var id: Long?,
    @SerializedName("userToInvite")
    @Expose
    var userToInvite: User,
    @SerializedName("userCar")
    @Expose
    var userCar: UserCar,
    @SerializedName("status")
    @Expose
    var status: String
)
