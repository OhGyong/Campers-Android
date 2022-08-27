package com.campers.data.community

/**
 * 게시물 상세
 */
data class CommunityDetailData(
    val id: Int,
    val title: String,
    val date: String,
    val nickName: String,
    val fireCount: Int,
    val viewCount: Int,
    val hotContents: Int,
    val hotDate: String,
    val reportCount: Int,
    val memberId: Int
)

/**
 * 게시물 상세 댓글
 */
data class CommunityCommentList(
    val id: Int,
//    val defaultBoardContentsId: Int,
    val info: String,
    val editDate: String,
    val fireCount: Int,
    val memberId: Int
)
