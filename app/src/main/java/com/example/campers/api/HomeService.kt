package com.example.campers.api

import com.example.campers.data.home.HotCommunityDetailRequest
import com.example.campers.data.home.HotCommunityDetailResponse
import com.example.campers.data.home.HotCommunityListResponse
import com.example.campers.data.home.RankingListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeService {

    @GET("/home/best-camper")
    fun rankingList(): Call<RankingListResponse>

    @GET("/home/hot-contents")
    fun hotCommunityList(): Call<HotCommunityListResponse>

    @POST("/home/hot-contents")
    fun hotCommunityDetail(@Body body: HotCommunityDetailRequest): Call<HotCommunityDetailResponse>

}