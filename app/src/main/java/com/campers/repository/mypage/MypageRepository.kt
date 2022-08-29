package com.campers.repository.mypage

import android.content.Context
import com.campers.api.CommonService
import com.campers.api.MypageService
import com.campers.data.mypage.ProfileResponse
import com.campers.repository.mypage.MypageApi.myPageResponse
import com.campers.util.CommonSharedPreferences

class MypageRepository {

    /**
     * 프로필 불러오기
     */
    fun getProfileData(context: Context): ProfileResponse {
        val refreshToken = CommonSharedPreferences(context).refreshToken.toString()
        val data = myPageResponse.getProfile(refreshToken).execute().body()
        return data!!
    }
}

/**
 * create 함수 호출 시 리소스가 많이 들기 때문에
 * 싱글톤 객체 선언을 통해 한번만 생성되도록 설정
 */
object MypageApi {
    val myPageResponse: MypageService by lazy {
        CommonService.retrofit.create(MypageService::class.java)
    }
}