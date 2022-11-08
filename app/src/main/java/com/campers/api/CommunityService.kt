package com.campers.api

import com.campers.data.community.*
import retrofit2.Call
import retrofit2.http.*

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
    fun communityDefaultDetailData(@Body body: DefaultBoardDetailRequest): Call<BoardDetailResponse>

    /**
     * 사용자 게시판 게시물 상세
     */
    @POST("/fire/member/content/detail")
    fun communityMemberDetailData(@Body body: MemberBoardDetailRequest): Call<BoardDetailResponse>

    /**
     * 사용자 게시판 게시물 글쓰기
     */
    @POST("/fire/member/content")
    fun communityMemberContentRegist(@Body body: CommunityContentRegistRequest): Call<CommonObjectResponse>

    /**
     * 사용자 게시판 게시물 댓글 작성
     */
    @POST("/fire/member/comment")
    fun communityMemberCommentRegist(@Body body: CommunityCommentRegistRequest): Call<CommonObjectResponse>

    /**
     * 사용자 게시판 게시물 좋아요
     */
    @PATCH("/fire/member/content/fire")
    fun communityMemberContentFire(@Body body: CommunityMemberContentFireRequest): Call<CommonArrayResponse>
}