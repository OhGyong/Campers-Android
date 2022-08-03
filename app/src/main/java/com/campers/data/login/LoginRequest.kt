package com.campers.data.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("socialId")
    val socialId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("socialPlatform")
    val socialPlatform: Int,
    @SerializedName("nickName")
    val nickName: String
)

data class naverLoginRequest(
    @SerializedName("response_type")
    val responseType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    @SerializedName("state")
    val state: String
)
