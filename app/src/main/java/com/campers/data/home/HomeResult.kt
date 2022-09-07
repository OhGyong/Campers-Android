package com.campers.data.home

/**
 * 랭킹 리스트
 */
data class RankingListResult(
    val success: RankingListResponse? = null,
    val failure: Exception? = null
)

data class RankingListData(
    val id: Int,
    val nickName: String,
    val rank: Int,
    val totalFire: Int
)


/**
 * 핫 게시물 리스트
 */
data class HotCommunityListResult(
    val success: HotCommunityListResponse? = null,
    val failure : Exception? = null
)

data class HotCommunityListData(
    val type: Int,
    val id: Int,
    val title: String,
    val date: String,
    val nickName: String
)

/**
 * 핫 게시물 상세
 */
data class HotCommunityDetailResult(
    val success: HotCommunityDetailResponse? = null,
    val failure : Exception? = null
)