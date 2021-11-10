package com.example.campers.api

import com.example.campers.data.login.LoginRequest
import com.example.campers.data.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/sign-in")
    fun signIn(@Body body: LoginRequest): Call<LoginResponse>
}