package com.example.campers.ui.login

import android.app.Activity


/**
 * 구글 로그인에 필요한 함수들을 모아놓은 클래스
 */
class GoogleLogin : Activity() {

    /**
     * 입력받은 토큰으로 유저의 정보를 가져오는 메서드
     * 유저의 정보가 받아지면 메인화면으로 이동.
     */
//    fun firebaseAuthWithGoogle(auth: FirebaseAuth, account: GoogleSignInAccount, context: AppCompatActivity): Boolean {
//        var returnStatus: Boolean = true
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val googleLoginInform = JSONObject()
//                    googleLoginInform.put("id", account.id)
//                    googleLoginInform.put("email", account.email)
//                    googleLoginInform.put("name", account.displayName)
//                    println("구글 로그인 정보 $googleLoginInform")
//                    Thread{
//                        val userAccessToken = LoginRepository().getLoginData(googleLoginInform, 1)
//                        SharedPreferences(context).accessToken = userAccessToken
//                    }.start()
//                    returnStatus = true
//                    println("11")
//                } else {
//                    println("로그인 실패 $task")
//                    returnStatus = false
//                }
//            }
//        return returnStatus
//    }
//    fun firebaseAuthWithGoogle(
//        auth: FirebaseAuth,
//        account: GoogleSignInAccount,
//        context: AppCompatActivity
//    ) {
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        auth.signInWithCredential(credential).addOnCompleteListener(Activity()) { task ->
//            GlobalScope.launch {
//                if (task.isSuccessful) {
//
//                    val googleLoginInform = JSONObject()
//                    googleLoginInform.put("id", account.id)
//                    googleLoginInform.put("email", account.email)
//                    googleLoginInform.put("name", account.displayName)
//                    println("구글 로그인 정보 $googleLoginInform")
//
//                    println("33")
//
//                    LoginRepository().getLoginData(googleLoginInform, 1)
//                    println("44")
//                    val userAccessToken = LoginRepository().getLoginData(googleLoginInform, 1)
//                    SharedPreferences(context).accessToken = userAccessToken
//                    println("55")
//                    LoginActivity().successLogin()
//
//                } else {
//                    println("로그인 실패 $task")
//                }
//            }
//        }
//        println("66")
//    }
}