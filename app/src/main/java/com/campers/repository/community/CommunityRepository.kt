package com.campers.repository.community

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
    fun getCommunityDefaultDetailData(defaultBoardContentsId: Int, memberId: Int): BoardDetailResponse {
        val request = DefaultBoardDetailRequest(
            defaultBoardContentsId,
            memberId
        )
        val data = communityResponse.communityDefaultDetailData(request).execute().body()
        return data!!
    }

    /**
     * 사용자 게시판 게시물 상세
     */
    fun getCommunityMemberDetailData(memberBoardContentsId: Int, memberId: Int): BoardDetailResponse {
        val request = MemberBoardDetailRequest(
            memberBoardContentsId,
            memberId
        )
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
}

object CommunityApi {
    val communityResponse: CommunityService by lazy {
        retrofit.create(CommunityService::class.java)
    }
}