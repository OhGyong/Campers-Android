package com.campers.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.login.SignInResult
import com.campers.repository.login.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel: ViewModel() {

    var signInData : MutableLiveData<SignInResult> = MutableLiveData()

    fun getSignInData(loginData: JSONObject, socialPlatform: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                signInData.postValue(
                    SignInResult(success = LoginRepository().getSignInData(loginData, socialPlatform))
                )
            }catch (e : Exception) {
                println("signIn API 에러 $e")
                signInData.postValue(
                    SignInResult(failure = e)
                )
            }
        }
    }



}