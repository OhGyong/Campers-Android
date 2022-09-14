package com.campers.api

import com.campers.data.community.CommunityDefaultResponse
import com.campers.data.community.CommunityListResponse
import com.campers.data.community.CommunityMemberResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommunityService {

    /**
     * 기본 게시판 목록 15개
     */
    @GET("/fire//default")
    fun communityDefault(): Call<CommunityDefaultResponse>

    /**
     * 사용자 게시판 목록 15개
     */
    @GET("/fire/member")
    fun communityMember(): Call<CommunityMemberResponse>

    /**
     * 기본 게시판 게시물 목록
     */
    @GET("/fire//default/content/list/{id}")
    fun communityDefaultList(@Path("id")id: Int): Call<CommunityListResponse>
}