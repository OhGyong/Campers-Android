package com.campers.ui.login

import android.app.Activity
import com.campers.ui.login.viewmodel.LoginViewModel
import com.campers.util.CommonObject.Companion.LoginJsonData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLogin {
    /**
     * registerForActivityResult 때문에 해당 함수만 분리시킴
     */
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, viewModel: LoginViewModel) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val auth = FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener(Activity()) { task ->
                if (task.isSuccessful) {
                    LoginJsonData.put("id", account.id)
                    LoginJsonData.put("email", account.email)
                    LoginJsonData.put("name", account.displayName)

                    viewModel.getSignInData(LoginJsonData, 1)
                } else {
                    println("로그인 실패 $task")
                }
            }
    }
}