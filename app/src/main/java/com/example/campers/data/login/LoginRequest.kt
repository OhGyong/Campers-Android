package com.example.campers.data.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("socialPlatform")
    val socialPlatform: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("rank")
    val rank: Int
)
