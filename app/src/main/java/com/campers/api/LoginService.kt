package com.campers.api

import com.campers.data.login.LoginRequest
import com.campers.data.login.SignInResponse
import com.campers.data.login.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/sign-in")
    fun signIn(@Body body: LoginRequest): Call<SignInResponse>

    @POST("/auth/sign-up")
    fun signUp(@Body body: LoginRequest): Call<SignUpResponse>

    @GET("https://openapi.naver.com/v1/nid/me")
    fun naverLogin()
}