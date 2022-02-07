package com.example.campers.api

import com.example.campers.data.home.HotCommunityListResponse
import com.example.campers.data.home.RankingListResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {

    @GET("/home/best-camper")
    fun rankingList(): Call<RankingListResponse>

    @GET("/home/hot-contents")
    fun hotCommunityList(): Call<HotCommunityListResponse>
}