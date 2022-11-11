package com.campers.data.community

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

/**
 * 기본 게시판 목록 15개
 */

/**
 * 기본 게시판 게시물 상세
 */
data class DefaultBoardDetailRequest(
    @SerializedName("defaultBoardContentsId")
    val defaultBoardContentsId: Int
)

/**
 * 사용자 게시판 게시물 상세
 */
data class MemberBoardDetailRequest(
    @SerializedName("memberBoardContentsId")
    val memberBoardContentsId: Int
)

/**
 * 사용자 게시판 게시물 글쓰기
 * -
 */
data class CommunityContentRegistRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("content")
    val contents: JSONObject,
    @SerializedName("memberBoardId")
    val memberBoardId: Int? = null,
    @SerializedName("defaultBoardId")
    val defaultBoardId: Int? = null,
    @SerializedName("memberId")
    val memberId: Int
)

/**
 * 사용자 게시판 게시물 댓글 작성
 */
data class CommunityCommentRegistRequest(
    @SerializedName("memberBoardContentsId")
    val memberBoardContentsId: Int,
    @SerializedName("info")
    val info: String,
    @SerializedName("editDate")
    val editDate: String,
    @SerializedName("memberId")
    val memberId: Int
)

/**
 * 사용자 게시판 게시물 좋아요
 */
data class CommunityMemberContentFireRequest(
    @SerializedName("memberBoardContentsId")
    val memberBoardContentsId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("memberBoardId")
    val memberBoardId: Int
)
