package com.campers.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * retrofit 호출 시 공통으로 쓰이는 부분만 따로 정리.
 * 싱글톤 객체로 선언
 */
object CommonService {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}