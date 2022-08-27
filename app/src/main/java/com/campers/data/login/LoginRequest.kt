package com.campers.data.login

import com.google.gson.annotations.SerializedName

/**
 * 로그인
 */
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
