package com.campers.repository.login

import com.campers.api.CommonService.retrofit
import com.campers.api.LoginService
import com.campers.data.login.LoginRequest
import com.campers.data.login.SignInResponse
import com.campers.data.login.SignUpResponse
import com.campers.repository.login.LoginApi.loginService
import org.json.JSONObject

class LoginRepository {

    fun getSignInData(signInDataParams: JSONObject, socialPlatform: Int): SignInResponse? {
        val data: SignInResponse?
        val request = LoginRequest(
            signInDataParams.getString("id"),
            signInDataParams.getString("email"),
            socialPlatform,
            signInDataParams.getString("name")
        )
        data = loginService.signIn(request).execute().body()
        return data
    }

    fun getSignUpData(signUpDataParams: JSONObject, socialPlatform: Int): SignUpResponse? {
        val data: SignUpResponse?
        val request = LoginRequest(
            signUpDataParams.getString("id"),
            signUpDataParams.getString("email"),
            socialPlatform,
            signUpDataParams.getString("name")
        )
        // 응답데이터 처리
        data = loginService.signUp(request).execute().body()
        return data!!
    }
}

object LoginApi {
    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }
}