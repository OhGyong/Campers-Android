package com.example.campers.data.home

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class RankingListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray,
    @SerializedName("data")
    val data: JsonObject
)

data class RankingList(
    val id: Int,
    val nickName: String,
    val rank: Int,
    val totalFire: Int
)

data class HotCommunityListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray,
    @SerializedName("data")
    val data: JsonObject
)

data class HotCommunityList(
    val type: Int,
    val id: Int,
    val title: String,
    val date: String,
    val nickName: String
)

data class HotCommunityDetailResponse(
    val status: Int,
    val payload: JsonArray
)