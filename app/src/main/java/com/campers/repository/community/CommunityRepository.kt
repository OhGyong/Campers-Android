package com.campers.repository.community

import com.campers.api.CommonArrayResponse
import com.campers.api.CommonObjectResponse
import com.campers.api.CommonService.retrofit
import com.campers.api.CommunityService
import com.campers.data.community.*
import com.campers.repository.community.CommunityApi.communityResponse

class CommunityRepository {

    /**
     * 기본 게시판 목록 15개
     */
    fun getCommunityDefaultData() : CommunityDefaultResponse {
        val data = communityResponse.communityDefault().execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 목록 15개
     */
    fun getCommunityMemberData() : CommunityMemberResponse {
        val data = communityResponse.communityMember().execute().body()
        return data!!
    }

    /**
     * 기본 게시판 게시물 목록
     */
    fun getCommunityDefaultListData(id: Int) : CommunityListResponse {
        val data = communityResponse.communityDefaultList(id).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 목록
     */
    fun getCommunityMemberListData(id: Int): CommunityListResponse {
        val data = communityResponse.communityMemberList(id).execute().body()
        return data!!
    }

    /**
     * 기본 게시판 게시물 상세
     */
    fun getCommunityDefaultDetailData(defaultBoardContentsId: Int): BoardDetailResponse {
        val request = DefaultBoardDetailRequest(defaultBoardContentsId)
        val data = communityResponse.communityDefaultDetailData(request).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 상세
     */
    fun getCommunityMemberDetailData(memberBoardContentsId: Int): BoardDetailResponse {
        val request = MemberBoardDetailRequest(memberBoardContentsId)
        val data = communityResponse.communityMemberDetailData(request).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 글쓰기
     */
    fun getCommunityMemberContentRegistData(request: CommunityContentRegistRequest): CommonObjectResponse {
        val data = communityResponse.communityMemberContentRegist(request).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 댓글 작성
     */
    fun getCommunityMemberCommentRegistData(request: CommunityCommentRegistRequest) : CommonObjectResponse {
        val data = communityResponse.communityMemberCommentRegist(request).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 좋아요
     */
    fun getCommunityContentFireData(request: CommunityMemberContentFireRequest) : CommonArrayResponse {
        val data = communityResponse.communityMemberContentFire(request).execute().body()
        return data!!
    }
}

object CommunityApi {
    val communityResponse: CommunityService by lazy {
        retrofit.create(CommunityService::class.java)
    }
}