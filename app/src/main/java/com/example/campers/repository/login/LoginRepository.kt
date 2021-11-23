package com.example.campers.repository.login

import com.example.campers.api.CommonService.service
import com.example.campers.data.login.LoginRequest
import com.example.campers.data.login.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * Login 결과의 accessToken을 반환
 * signCode 0은 signIn, 1은 signUp
 */
@Suppress("NAME_SHADOWING")
class LoginRepository {

    suspend fun getLoginData(signCode: Int, loginData: JSONObject, socialPlatform: Int): LoginResponse = withContext(Dispatchers.Default) {
        // 요청데이터 작성
        val request = LoginRequest(
            loginData.getString("id"),
            loginData.getString("email"),
            socialPlatform,
            loginData.getString("name")
        )
        println("요청데이터 $request")

        if(signCode == 0){
            // 응답데이터 처리
            val response = service.signIn(request).execute()

            val loginData = response.body()?.data
            println("로그인 데이터 확인 ${response.body()}")

            return@withContext response.body()!!
        }else{
            // 응답데이터 처리
            val response = service.signUp(request).execute()

            val loginData = response.body()?.data
            println("로그인 데이터 확인 ${response.body()}")

            return@withContext response.body()!!
        }


//        /**
//         * 응답 데이터에서 status가 301일 경우 AlertDialog를 호출하여
//         * 닉네임을 변경하면서 다시 데이터 요청
//         */
//        if(response.body()?.status == 301){
//            return@withContext loginData.toString()
//        }
//
//        // gson의 JsonObject의 jsonElement로 데이터를 꺼내옴
//        val accessToken = loginData?.get("accessToken")
//        println("액세스 토큰 확인 $accessToken")
//        return@withContext accessToken.toString()
    }
}