package com.campers.repository.home

import com.campers.api.CommonService.retrofit
import com.campers.api.HomeService
import com.campers.data.home.HotCommunityDetailRequest
import com.campers.data.home.HotCommunityDetailResponse
import com.campers.data.home.HotCommunityListResponse
import com.campers.data.home.RankingListResponse
import com.campers.repository.home.HomeApi.homeResponse
import kotlinx.coroutines.runBlocking

class HomeRepository {

    /**
     * 랭킹 리스트
     */
    fun getRankingListData(): RankingListResponse {
        val data = homeResponse.rankingList().execute().body()
        return data!!
    }

    /**
     * 핫 게시물 리스트
     */
    fun getHotCommunityListData(): HotCommunityListResponse {
        val data = homeResponse.hotCommunityList().execute().body()
        return data!!
    }

    /**
     * 핫 게시물 상세
     * type이 1이면 유저 게시판
     * type이 2면 기본 게시판
     */
    fun getHotCommunityDetailData(type: Int, id: Int): HotCommunityDetailResponse {
        val request: HotCommunityDetailRequest = if (type == 1) {
            HotCommunityDetailRequest(
                type,
                id,
                null
            )
        } else {
            HotCommunityDetailRequest(
                type,
                null,
                id
            )

        }
        val data = homeResponse.hotCommunityDetail(request).execute().body()
        return data!!
    }
}


/**
 * create 함수 호출 시 리소스가 많이 들기 때문에
 * 싱글톤 객체 선언을 통해 한번만 생성되도록 설정
 */
object HomeApi {
    val homeResponse: HomeService by lazy {
        retrofit.create(HomeService::class.java)
    }
}