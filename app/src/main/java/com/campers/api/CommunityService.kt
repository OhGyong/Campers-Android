package com.campers.api

import com.campers.data.community.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityService {

    /**
     * 기본 게시판 목록 15개
     */
    @GET("/fire/default")
    fun communityDefault(): Call<CommunityDefaultResponse>

    /**
     * 사용자 게시판 목록 15개
     */
    @GET("/fire/member")
    fun communityMember(): Call<CommunityMemberResponse>

    /**
     * 기본 게시판 게시물 목록
     */
    @GET("/fire/default/content/list/{id}")
    fun communityDefaultList(@Path("id")id: Int): Call<CommunityListResponse>

    /**
     * 사용자 게시판 게시물 목록
     */
    @GET("/fire/member/content/list/{id}")
    fun communityMemberList(@Path("id")id: Int): Call<CommunityListResponse>

    /**
     * 기본 게시판 게시물 상세
     */
    @POST("/fire/default/content/detail")
    fun communityDefaultDetailData(@Body body: DefaultBoardDetailRequest): Call<DefaultBoardDetailResponse>
}