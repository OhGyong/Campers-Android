package com.campers.ui.login

import android.annotation.SuppressLint
import android.content.Context
import com.campers.ui.BaseActivity
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class NaverLogin : BaseActivity(){
    /**
     * 네이버 로그인
     * 로그인이 완료되거나 취소될 때 호출되는 메서드(로그인 핸들러)
     */
    fun naverLoginHandler(context: Context, viewModel: LoginViewModel ,naverLoginInstance: OAuthLogin) : OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if(success){
                val naverSignInInform = JSONObject()
                /**
                 * 1. runBlocking 안에 코루틴을 실행시키고 네이버 유저 정보를 받아온다.
                 * 2. 네이버 유저 정보를 다 얻을때까지 join()을 통해 기다린다.
                 * 3. 정보를 다 얻어오면 로그인 api 호출
                 * → 원래는 Coroutine 안에서 다 처리할 수 있지만 ViewModel에서 CoroutineScope를 실행시켜 로직을 분리하기 위해 이 방법을 사용했다.
                 */
                runBlocking {
                    val naverUserInformLaunch = CoroutineScope(Dispatchers.IO).launch {
                        try {
                            naverSignInInform.put("id", loginInform(naverLoginInstance.getAccessToken(context)).get("id"))
                            naverSignInInform.put("email", loginInform(naverLoginInstance.getAccessToken(context)).get("email"))
                            naverSignInInform.put("name", loginInform(naverLoginInstance.getAccessToken(context)).get("name"))
                        }catch (e:Exception){
                            // TODO : 에러 바텀 시트를 띄우자
                        }
                    }
                    naverUserInformLaunch.join() // 네이버 회원 정보 가져올 때까지 대기
                    viewModel.getSignInData(naverSignInInform, 2)
                }
            }else{
                // TODO : 에러 바텀 시트를 띄우자
            }
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
            // TODO : 에러 바텀 시트를 띄우자
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
            // TODO : 에러 바텀 시트를 띄우자
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl, $error")
        } catch (error: IOException) {
            // TODO : 에러 바텀 시트를 띄우자
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
            // TODO : 에러 바텀 시트를 띄우자
            throw java.lang.RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }
}