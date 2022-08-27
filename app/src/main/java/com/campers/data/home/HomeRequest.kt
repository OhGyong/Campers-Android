package com.campers.data.home

/**
 * 핫 게시물 상세
 */
data class HotCommunityDetailRequest(
    val type: Int,
    val memberBoardContentsId: Int?,
    val defaultBoardContentsId: Int?
)