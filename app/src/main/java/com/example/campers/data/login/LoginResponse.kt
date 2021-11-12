package com.example.campers.data.login

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payloaod: JsonObject,
    @SerializedName("data")
    val data: JsonObject
)
