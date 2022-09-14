package com.campers.data.community

/**
 * 기본 게시판 및 사용자 게시판 15개에 사용할 공통 data class
 */
data class CommunityBoardData(
    val id: Int,
    val name: String
)

/**
 * 기본 게시판 목록 15개
 */
data class CommunityDefaultResult(
    val success: CommunityDefaultResponse? = null,
    val failure: Exception? = null
)

/**
 * 기본 게시판 목록 15개
 */
data class CommunityMemberResult(
    val success: CommunityMemberResponse? = null,
    val failure: Exception? = null
)

/**
 * 게시글 리스트
 * - 기본 게시판 게시물 목록
 */
data class CommunityListResult(
    val success: CommunityListResponse? = null,
    val failure: Exception? = null
)

data class CommunityListData(
    val id: Int,
    val title: String,
    val date: String,
    val editDate: String,
    val nickName: String,
    val fireCount: Int,
    val viewCount: Int
)

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
 * 게시물 상세 댓글, 유저 게시판
 */
data class CommunityCommentData(
    val id: Int,
    val memberBoardContentsId: Int,
    val defaultBoardContentsId: Int,
    val info: String,
    val editDate: String,
    val fireCount: Int,
    val memberId: Int
)

/**
 * 임시 데이터
 */
data class CommunityData(
    val userName: String,
    val communityTitle: String,
    val bornfireNum: Int,
    val commentNum: Int,
    val viewNum: Int
)
