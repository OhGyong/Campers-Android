package com.example.campers.repository.home

import com.example.campers.api.CommonService.retrofit
import com.example.campers.api.HomeService
import com.example.campers.data.home.HotCommunityDetailRequest
import com.example.campers.data.home.HotCommunityDetailResponse
import com.example.campers.data.home.HotCommunityListResponse
import com.example.campers.data.home.RankingListResponse
import com.example.campers.repository.home.HomeApi.homeResponse
import kotlinx.coroutines.runBlocking

class HomeRepository {

    fun getRankingData(): RankingListResponse {
        var data: RankingListResponse?
        runBlocking {
            println("Repository 랭킹 데이터")
            // 데이터 가져오기
            data = homeResponse.rankingList().execute().body()
        }
        return data!!
    }

    fun getHotCommunityListData(): HotCommunityListResponse {
        var data: HotCommunityListResponse?
        runBlocking {
            println("Repository Hot 커뮤니티 데이터")
            data = homeResponse.hotCommunityList().execute().body()
        }
        return data!!
    }

    /**
     * type이 1이면 유저 게시판
     * type이 2면 기본 게시판
     */
    fun getHotCommunityDetailData(type: Int, id: Int): HotCommunityDetailResponse {
        var data: HotCommunityDetailResponse?
        println("Repository Hot 커뮤니티 상세 데이터")
        var request: HotCommunityDetailRequest = if (type == 1) {
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
        data = homeResponse.hotCommunityDetail(request).execute().body()
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