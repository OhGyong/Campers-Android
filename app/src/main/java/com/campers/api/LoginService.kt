package com.campers.api

import com.campers.data.login.LoginRequest
import com.campers.data.login.SignInResponse
import com.campers.data.login.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/sign-in")
    suspend fun signIn(@Body body: LoginRequest): Call<SignInResponse>

    @POST("/auth/sign-up")
    fun signUp(@Body body: LoginRequest): Call<SignUpResponse>
}