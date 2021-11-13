package com.example.campers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.campers.MainActivity
import com.example.campers.R
import com.example.campers.repository.login.LoginRepository
import com.example.campers.util.SharedPreferences
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * 소셜 로그인
 */
class LoginActivity : Activity() {

    lateinit var mOAuthLoginInstance: OAuthLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**
         * 네이버 로그인 라이브러리 애플리케이션 적용(네이버 로그인 인스턴스 초기화)
         */
        val naverClientId = getString(R.string.naver_login_id)
        val naverClientSecret = getString(R.string.naver_login_secret)
        val naverClientName = getString(R.string.naver_login_name)

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(this, naverClientId, naverClientSecret, naverClientName)

        val buttonOAuthLoginImg = findViewById<OAuthLoginButton>(R.id.naverLoginButton)

        // 네이버 로그인 버튼 클릭 시 로그인 핸들러 실행
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
    }

    /**
     * 로그인이 완료되거나 취소될 때 호출되는 메서드(로그인 핸들러)
     */
    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {

            // 로그인 성공시
            if (success) {

                /**
                 * 서버통신을 하기 때문에 스레드 사용.
                 * 네이버 오픈 API를 사용하기 위해 mOAuthLoginInstance에 오픈 API의 accessToken을 넘겨줌.
                 * 얻은 데이터를 LoginRepository()에 넘겨주고 서버와 연결하여 유저의 accessToken을 발급받음.
                 */
                Thread {
                    val userAccessToken = LoginRepository().getLoginData(loginInform(mOAuthLoginInstance.getAccessToken(applicationContext)))
                    SharedPreferences(this@LoginActivity).accessToken = userAccessToken
                }.start()

                // 메인화면으로 이동
                successLogin()
            } else { // 로그인 실패시
                val errorCode: String =
                    mOAuthLoginInstance.getLastErrorCode(this@LoginActivity).code
                val errorDesc = mOAuthLoginInstance.getLastErrorDesc(this@LoginActivity)

                Toast.makeText(
                    baseContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * 현재 로그인된 정보를 가져오는 메서드
     */
    fun loginInform(accessToken: String): JSONObject {
        val header = "Bearer $accessToken"
        val apiUrl = "https://openapi.naver.com/v1/nid/me"
        val requestHeaders = mutableMapOf<String, String>()
        requestHeaders["Authorization"] = header
        val responseBody = get(apiUrl, requestHeaders)

        // api를 호출하여 얻어온 결과에서 id, email, name 가져오는 과정
        val response = responseBody.getJSONObject("response")
        println("로그인 정보 $responseBody")
        return response
    }

    /**
     * 네이버 회원 api 호출하여 정보 가져오는 메서드
     */
    private fun get(apiUrl: String, requestHeaders: Map<String, String>): JSONObject {
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

    /**
     * 로그인 성공했을 경우 메인화면으로 이동하는 메서드
     */
    fun successLogin() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}