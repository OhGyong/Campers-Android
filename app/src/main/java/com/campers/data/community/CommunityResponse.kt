package com.campers.data.community

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

/**
 * 기본 게시판 목록 15개
 */
data class CommunityDefaultResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)

/**
 * 사용자 게시판 목록 15개
 */
data class CommunityMemberResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)

/**
 * 게시글 리스트
 * - 기본 게시판 게시물 목록
 */
data class CommunityListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)

/**
 * 기본 게시판 게시물 상세
 */
data class DefaultBoardDetailResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)