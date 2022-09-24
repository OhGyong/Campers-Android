package com.campers.data.community

import com.google.gson.annotations.SerializedName

/**
 * 기본 게시판 목록 15개
 */

/**
 * 기본 게시판 게시물 상세
 */
data class DefaultBoardDetailRequest(
    @SerializedName("defaultBoardContentsId")
    val defaultBoardContentsId: Int,
    @SerializedName("memberId")
    val memberId: Int
)
