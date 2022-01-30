package com.example.campers.api

import com.example.campers.data.home.RankingResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {
    @GET("/home/best-camper")
    fun rankingDisplay(): Call<RankingResponse>
}