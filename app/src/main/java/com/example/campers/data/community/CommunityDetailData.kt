package com.example.campers.data.community

data class CommunityDetailData(
    val id: Int,
    val title: String,
    val date: String,
    val nickName: String,
    val fireCount: Int,
    val viewCount: Int,
//    val contents: RichEditor,
    val hotContents: Int,
    val hotDate: String,
    val reportCount: Int,
    val memberId: Int
)
