package com.example.campers.data.home

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class RankingResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray,
    @SerializedName("data")
    val data: JsonObject
)

data class Ranking(
    val id: Int,
    val nickName: String,
    val rank: Int,
    val totalFire: Int
)