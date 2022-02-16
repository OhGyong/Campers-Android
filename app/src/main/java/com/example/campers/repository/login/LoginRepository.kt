package com.example.campers.repository.login

import com.example.campers.api.CommonService.retrofit
import com.example.campers.api.LoginService
import com.example.campers.data.login.LoginRequest
import com.example.campers.data.login.SignInResponse
import com.example.campers.data.login.SignUpResponse
import com.example.campers.repository.login.LoginApi.loginService
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

/**
 * Login 결과의 accessToken을 반환
 */
class LoginRepository {

    fun getSignInData(loginData: JSONObject, socialPlatform: Int): SignInResponse {
        var data: SignInResponse?
        runBlocking {
            // 요청데이터 작성
            val request = LoginRequest(
                loginData.getString("id"),
                loginData.getString("email"),
                socialPlatform,
                loginData.getString("name")
            )

            data = loginService.signIn(request).execute().body()
            println("로그인 데이터 확인 $data")
        }
        return data!!
    }

    fun getSignUpData(loginData: JSONObject, socialPlatform: Int): SignUpResponse{
        var data: SignUpResponse?
        runBlocking {
            // 요청데이터 작성
            val request = LoginRequest(
                loginData.getString("id"),
                loginData.getString("email"),
                socialPlatform,
                loginData.getString("name")
            )

            // 응답데이터 처리
            data = loginService.signUp(request).execute().body()
            println("회원가입 데이터 확인 $data")
        }
        return data!!
    }
}

object LoginApi {
    val loginService: LoginService by lazy{
        retrofit.create(LoginService::class.java)
    }
}