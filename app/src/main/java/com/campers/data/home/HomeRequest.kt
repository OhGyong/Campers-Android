package com.campers.data.home

data class HotCommunityDetailRequest(
    val type: Int,
    val memberBoardContentsId: Int?,
    val defaultBoardContentsId: Int?
)