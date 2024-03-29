package com.campers.api

import com.campers.data.home.HotCommunityDetailRequest
import com.campers.data.home.HotCommunityDetailResponse
import com.campers.data.home.HotCommunityListResponse
import com.campers.data.home.RankingListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeService {

    /**
     * 랭킹 리스트
     */
    @GET("/home/best-camper")
    fun rankingList(): Call<RankingListResponse>

    /**
     * 핫 게시물 리스트
     */
    @GET("/home/hot-contents")
    fun hotCommunityList(): Call<HotCommunityListResponse>

    /**
     * 핫 게시물 상세
     */
    @POST("/home/hot-contents")
    fun hotCommunityDetail(@Body body: HotCommunityDetailRequest): Call<HotCommunityDetailResponse>
}