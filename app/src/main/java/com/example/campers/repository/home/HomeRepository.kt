package com.example.campers.repository.home

import com.example.campers.api.CommonService.retrofit
import com.example.campers.api.HomeService
import com.example.campers.data.home.RankingResponse
import kotlinx.coroutines.runBlocking

class HomeRepository {

    fun getRankingData(): RankingResponse {
        var data: RankingResponse?
        runBlocking {
            println("Repository")

            // 데이터 가져오기
            data = RankingApi.rankingResponse.rankingDisplay().execute().body()
        }
        return data!!
    }
}


/**
 * create 함수 호출 시 리소스가 많이 들기 때문에
 * 싱글톤 객체 선언을 통해 한번만 생성되도록 설정
 */
object RankingApi {
    val rankingResponse: HomeService by lazy{
        retrofit.create(HomeService::class.java)
    }
}