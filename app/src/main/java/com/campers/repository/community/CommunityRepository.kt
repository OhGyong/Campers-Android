package com.campers.repository.community

import com.campers.api.CommonService.retrofit
import com.campers.api.CommunityService
import com.campers.data.community.CommunityDefaultResponse
import com.campers.data.community.CommunityMemberResponse
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
     * 기본 게시판 목록 15개
     */
    fun getCommunityMemberData() : CommunityMemberResponse {
        val data = communityResponse.communityMember().execute().body()
        return data!!
    }

}

object CommunityApi {
    val communityResponse: CommunityService by lazy {
        retrofit.create(CommunityService::class.java)
    }
}