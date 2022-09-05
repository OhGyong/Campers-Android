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