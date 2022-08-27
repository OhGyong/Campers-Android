package com.campers.data.home

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * 랭킹 리스트
 */
data class RankingListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray,
    @SerializedName("data")
    val data: JsonObject
)

/**
 * 핫 게시물 리스트
 */
data class HotCommunityListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray,
    @SerializedName("data")
    val data: JsonObject
)

/**
 * 핫 게시물 상세
 */
data class HotCommunityDetailResponse(
    val status: Int,
    val payload: JsonArray
)

