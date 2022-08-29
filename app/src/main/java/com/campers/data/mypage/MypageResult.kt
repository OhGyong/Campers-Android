package com.campers.data.mypage

/**
 * 프로필 불러오기
 */
data class ProfileResult(
    val success: ProfileResponse? = null,
    val failure: Exception? = null
)

data class ProfileData(
    val id: Int? = null,
    val nickName: String? = null,
    val totalFire: Int? = null,
    val rank: Int? = null
)