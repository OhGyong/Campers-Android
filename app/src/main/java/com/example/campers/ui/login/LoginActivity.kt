package com.example.campers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.campers.MainActivity
import com.example.campers.R
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class LoginActivity : Activity() {

    lateinit var mOAuthLoginInstance: OAuthLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val naverClientId = getString(R.string.naver_login_id)
        val naverClientSecret = getString(R.string.naver_login_secret)
        val naverClientName = getString(R.string.naver_login_name)

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(this, naverClientId, naverClientSecret, naverClientName)

        val buttonOAuthLoginImg = findViewById<OAuthLoginButton>(R.id.naverLoginButton)

        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
    }

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                Log.d("로그인 accesstoken", mOAuthLoginInstance.getAccessToken(applicationContext))
                Log.d("로그인 refreshtoken", mOAuthLoginInstance.getRefreshToken(applicationContext))

                val thread = Thread {
                    loginInform(mOAuthLoginInstance.getAccessToken(applicationContext))
                }.start()


                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
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

    fun loginInform(accessToken: String) {
        val header = "Bearer $accessToken"
        val apiUrl = "https://openapi.naver.com/v1/nid/me"
        val requestHeaders = mutableMapOf<String, String>()
        requestHeaders["Authorization"] = header
        val responseBody = get(apiUrl, requestHeaders)

        println("로그인 정보 $responseBody")
    }

    private fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val con = connect(apiUrl)
        try {
            con.requestMethod = "GET"

            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) {
                readBody(con.inputStream)
            }else{
                readBody(con.errorStream)
            }
        }catch (error: IOException){
            throw RuntimeException("API 요청과 응답 실패 $error")
        }finally {
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

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)
        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String?
                while (lineReader.readLine().also { line = it } != null) {
                    responseBody.append(line)
                }
                return responseBody.toString()
            }
        } catch (e: IOException) {
            throw java.lang.RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }
}