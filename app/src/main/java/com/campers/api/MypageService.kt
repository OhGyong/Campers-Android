package com.campers.api

import com.campers.data.mypage.ProfileResponse
import retrofit2.Call
import retrofit2.http.*

interface MypageService {

    /**
     * 프로필 불러오기
     */
    @GET("/mypage/profile")
    fun getProfile(@Header("Authorization") token: String): Call<ProfileResponse>
}