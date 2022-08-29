package com.campers.data.mypage

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

/**
 * 프로필 불러오기
 */
data class ProfileResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)