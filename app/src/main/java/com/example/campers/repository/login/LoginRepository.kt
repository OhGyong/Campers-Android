package com.example.campers.repository.login

import com.example.campers.api.CommonService.service
import com.example.campers.data.login.LoginRequest
import org.json.JSONObject

/**
 * Login 결과의 accessToken을 반환
 */
class LoginRepository {

    fun getLoginData(loginData: JSONObject, socialPlatform: Int): String{

        // 요청데이터 작성
        val request = LoginRequest(loginData.getString("id"), loginData.getString("email"), socialPlatform, loginData.getString("name"))
        println("요청데이터 $request")

        // 응답데이터 처리
        val response = service.signIn(request).execute()

        val loginData = response.body()?.data
        println("로그인 데이터 확인 ${response.body()}")

        // gson의 JsonObject의 jsonElement로 데이터를 꺼내옴
        val accessToken = loginData?.get("accessToken")
        println("액세스 토큰 확인 $accessToken")

        return accessToken.toString()
    }
}