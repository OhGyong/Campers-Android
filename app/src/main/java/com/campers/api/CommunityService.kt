package com.campers.api

import com.campers.data.community.CommunityDefaultResponse
import retrofit2.Call
import retrofit2.http.GET

interface CommunityService {

    /**
     * 기본 게시판 목록 15개
     */
    @GET("/fire//default")
    fun communityDefault(): Call<CommunityDefaultResponse>
}