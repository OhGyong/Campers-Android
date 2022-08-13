package com.campers.data.login

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName

/**
 * SignInResponse, SignUpResponse 둘로 나눈 이유는 payload에서 Json 타입문제 때문에 나눔
 */
data class SignInResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payloaod: JsonPrimitive,
    @SerializedName("data")
    val data: JsonObject
)

data class SignUpResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payloaod: JsonObject,
    @SerializedName("data")
    val data: JsonObject
)


data class SignInResult(
    val success : SignInResponse? = null,
    val failure : Exception? = null
)

data class SignUpResult(
    val success : SignUpResponse? = null,
    val failure : Exception? = null
)
