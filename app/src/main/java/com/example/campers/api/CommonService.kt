package com.example.campers.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * retrofit 호출 시 공통으로 쓰이는 부분만 따로 정리.
 * 싱글톤 객체로 선언
 */
object CommonService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(LoginService::class.java)!!
}