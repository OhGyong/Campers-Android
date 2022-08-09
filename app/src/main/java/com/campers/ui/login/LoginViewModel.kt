package com.campers.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.login.SignInResponse
import com.campers.repository.login.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel: ViewModel() {

    var signInData : MutableLiveData<SignInResponse> = MutableLiveData()

    fun getSignInData(loginData: JSONObject, socialPlatform: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                signInData.postValue(LoginRepository().getSignInData(loginData, socialPlatform))
            }catch (e : Exception) {
                println("로그인 에러 $e")
            }
        }
    }



}