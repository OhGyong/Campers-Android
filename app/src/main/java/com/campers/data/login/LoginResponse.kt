package com.campers.data.login

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName

/**
 * 로그인.
 * status가 301로 떨어지면 회원가입으로 넘어감.
 */
data class SignInResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payloaod: JsonPrimitive,
    @SerializedName("data")
    val data: JsonObject
)


/**
 * 회원가입
 */
data class SignUpResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payloaod: JsonObject,
    @SerializedName("data")
    val data: JsonObject
)