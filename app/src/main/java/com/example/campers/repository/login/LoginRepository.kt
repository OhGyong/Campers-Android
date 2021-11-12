package com.example.campers.repository.login

import com.example.campers.api.CommonService.service
import com.example.campers.data.login.LoginRequest
import com.example.campers.data.login.LoginResponse
import org.json.JSONObject

class LoginRepository {

    fun getLoginData(loginData: JSONObject){

        // 요청데이터 작성
        val request = LoginRequest(loginData.getString("id"), loginData.getString("email"), 2, loginData.getString("name"),0)

        // 응답데이터 처리
        val response = service.signIn(request).execute()

        if(response.isSuccessful){
            val data: LoginResponse? = response.body()

//            println("응답 데이터 ${Gson().toJson(data)}")
            println("응답 데이터 ${data.toString()}")
        }else{
            println("에러")
        }
    }
}