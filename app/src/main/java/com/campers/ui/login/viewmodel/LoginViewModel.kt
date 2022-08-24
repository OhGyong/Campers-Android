package com.campers.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.campers.data.login.SignInResult
import com.campers.data.login.SignUpResult
import com.campers.repository.login.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel: ViewModel() {

    var signInData : MutableLiveData<SignInResult> = MutableLiveData()
    var signUpData : MutableLiveData<SignUpResult> = MutableLiveData()

    fun getSignInData(signInDataParams: JSONObject, socialPlatform: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                signInData.postValue(
                    SignInResult(success = LoginRepository().getSignInData(signInDataParams, socialPlatform))
                )
            }catch (e : Exception) {
                signInData.postValue(
                    SignInResult(failure = e)
                )
            }
        }
    }

    fun getSignUpData(signUpDataParams: JSONObject, socialPlatform: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                signUpData.postValue(
                    SignUpResult(success = LoginRepository().getSignUpData(signUpDataParams, socialPlatform))
                )
            }catch (e : Exception) {
                signUpData.postValue(
                    SignUpResult(failure = e)
                )
            }
        }
    }



}