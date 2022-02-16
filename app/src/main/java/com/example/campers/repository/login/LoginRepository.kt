package com.example.campers.repository.login

import com.example.campers.api.CommonService.retrofit
import com.example.campers.api.LoginService
import com.example.campers.data.login.LoginRequest
import com.example.campers.data.login.SignInResponse
import com.example.campers.data.login.SignUpResponse
import com.example.campers.repository.login.LoginApi.loginService
import org.json.JSONObject

/**
 * Login 결과의 accessToken을 반환
 */
@Suppress("NAME_SHADOWING")
class LoginRepository {

    fun getSignInData(loginData: JSONObject, socialPlatform: Int): SignInResponse {
        // 요청데이터 작성
        val request = LoginRequest(
            loginData.getString("id"),
            loginData.getString("email"),
            socialPlatform,
            loginData.getString("name")
        )
        println("요청데이터 $request")

        val response = loginService.signIn(request).execute()
        println("로그인 데이터 확인 ${response.body()}")
        return response.body()!!

    }

    fun getSignUpData(loginData: JSONObject, socialPlatform: Int): SignUpResponse{
        // 요청데이터 작성
        val request = LoginRequest(
            loginData.getString("id"),
            loginData.getString("email"),
            socialPlatform,
            loginData.getString("name")
        )
        println("요청데이터 $request")

        // 응답데이터 처리
        val response = loginService.signUp(request).execute()
        println("회원가입 데이터 확인 ${response.body()}")
        return response.body()!!
    }
}

object LoginApi {
    val loginService: LoginService by lazy{
        retrofit.create(LoginService::class.java)
    }
}