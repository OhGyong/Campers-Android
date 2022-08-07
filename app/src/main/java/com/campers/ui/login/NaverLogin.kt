package com.campers.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class NaverLogin : AppCompatActivity(){
    /**
     * 네이버 로그인
     * 로그인이 완료되거나 취소될 때 호출되는 메서드(로그인 핸들러)
     */
    fun naverLoginHandler(context: Context, viewModel: LoginViewModel ,naverLoginInstance: OAuthLogin) : OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if(success){
                val naverSignInInform = JSONObject()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        naverSignInInform.put("id", loginInform(naverLoginInstance.getAccessToken(context)).get("id"))
                        naverSignInInform.put("email", loginInform(naverLoginInstance.getAccessToken(context)).get("email"))
                        naverSignInInform.put("name", loginInform(naverLoginInstance.getAccessToken(context)).get("name"))
                        println("naverSignInInform $naverSignInInform")
                        viewModel.getSignInData(naverSignInInform, 2)
                    }catch (e:Exception){
                        println("NaverLogin $e")

                    }
                }
            }else{
                println("네이버 로그인 실패")
            }


            // 로그인 성공시
//            if (success) {
//                /**
//                 * 서버통신을 하기 때문에 스레드 사용.
//                 * 네이버 오픈 API를 사용하기 위해 mOAuthLoginInstance에 오픈 API의 accessToken을 넘겨줌.
//                 * 얻은 데이터를 LoginRepository()에 넘겨주고 서버와 연결하여 유저의 accessToken을 발급받음.
//                 */
//                runBlocking {
//                    val naverSignInInform = JSONObject()
//                    GlobalScope.launch {
//                        naverSignInInform.put("id", loginInform(naverLoginInstance.getAccessToken(applicationContext)).get("id"))
//                        naverSignInInform.put("email", loginInform(naverLoginInstance.getAccessToken(applicationContext)).get("email"))
//                        naverSignInInform.put("name", loginInform(naverLoginInstance.getAccessToken(applicationContext)).get("name"))
//                        println("네이버 로그인 정보 $naverSignInInform")
//                        loginInform(naverLoginInstance.getAccessToken(applicationContext))
//                        try {
////                            signInData = LoginRepository().getSignInData(naverSignInInform, 2)
//                        }catch (e: Error){
//                            println("로그인 실패 $e")
//                        }
//                    }.join()
//                    if (signInData.status == 301) {
//                        // 회원가입을 위해 닉네임을 입력하기 위한 다이얼로그 호출
//                        signUpAlertDialog(naverSignInInform)
//                    }
//                    // 기존 아이디가 존재하여 로그인을 하는 경우
//                    else {
//                        userAccessToken = signInData.data.get("accessToken").toString()
//                        SharedPreferences(context).accessToken = userAccessToken
//                        successLogin()
//                    }
//                }
//            } else { // 로그인 실패시
//                val errorCode: String =
//                    naverLoginInstance.getLastErrorCode(context).code
//                val errorDesc = naverLoginInstance.getLastErrorDesc(context)
//
//                Toast.makeText(
//                    baseContext, "errorCode:" + errorCode
//                            + ", errorDesc:" + errorDesc, Toast.LENGTH_LONG
//                ).show()
//            }

        }
    }



    /**
     * 네이버 로그인
     * 현재 로그인된 정보를 가져오는 메서드
     */
    fun loginInform(accessToken: String): JSONObject {
        val header = "Bearer $accessToken"
        val requestHeaders = mutableMapOf<String, String>()
        requestHeaders["Authorization"] = header
        val responseBody = get(requestHeaders)

        // api를 호출하여 얻어온 결과에서 id, email, name 가져오는 과정
        return responseBody.getJSONObject("response")
    }

    /**
     * 네이버 로그인
     * 네이버 회원 api 호출하여 정보 가져오는 메서드
     */
    private fun get(requestHeaders: Map<String, String>): JSONObject {
        val con = connect("https://openapi.naver.com/v1/nid/me")
        try {
            con.requestMethod = "GET"

            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) {
                readBody(con.inputStream)
            } else {
                readBody(con.errorStream)
            }
        } catch (error: IOException) {
            throw RuntimeException("API 요청과 응답 실패 $error")
        } finally {
            con.disconnect()
        }

    }

    /**
     * 네이버 로그인
     */
    private fun connect(apiUrl: String): HttpURLConnection {
        try {
            val url = URL(apiUrl)
            return url.openConnection() as HttpURLConnection
        } catch (error: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl, $error")
        } catch (error: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl, $error")
        }
    }


    /**
     * 네이버 로그인
     * 서버에서 가져오는 데이터를 json 텍스트로 변환해주는 메서드
     */
    private fun readBody(body: InputStream): JSONObject {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String?
                while (lineReader.readLine().also { line = it } != null) {
                    responseBody.append(line)
                }
                return JSONObject(responseBody.toString())
            }
        } catch (e: IOException) {
            throw java.lang.RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }
}