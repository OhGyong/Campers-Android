package com.example.campers.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.campers.MainActivity
import com.example.campers.R
import com.example.campers.repository.login.LoginRepository
import com.example.campers.util.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

    // 네이버 로그인 설정
    lateinit var mOAuthLoginInstance: OAuthLogin

    // 구글 로그인 설정
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var userAccessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 구글 로그인 버튼 텍스트 변경
        val googleLoginButton = findViewById<SignInButton>(R.id.googleLoginButton)
        setGoogleButtonText(googleLoginButton, "구글 아이디로 로그인")

        // 네이버 로그인 라이브러리 애플리케이션 적용(네이버 로그인 인스턴스 초기화)
        naverLoginInit()

        // 구글 로그인 인스턴스 초기화
        googleLoginInit()
    }


    /**
     * 구글 로그인
     * 구글 startActivityForResult로 입력된 데이터를 받고 실행되는 메서드
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (error: ApiException) {
                println("onActivity 실패 $error")
            }
        }else{
            println("느낌이 여기")
        }
    }

    /**
     * 구글 로그인
     * 입력받은 토큰으로 유저의 정보를 가져오는 메서드
     * 유저의 정보가 받아지면 메인화면으로 이동.
     */
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val googleLoginInform = JSONObject()
                    googleLoginInform.put("id", account.id)
                    googleLoginInform.put("email", account.email)
                    googleLoginInform.put("name", account.displayName)
                    println("구글 로그인 정보 $googleLoginInform")
                    Thread{
                        userAccessToken = LoginRepository().getLoginData(googleLoginInform, 1)
                        SharedPreferences(this@LoginActivity).accessToken = userAccessToken
                    }.start()

                    successLogin()
                } else {
                    println("로그인 실패 $task")
                }
            }
    }

    /**
     * 네이버 로그인
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
                    userAccessToken = LoginRepository().getLoginData(loginInform(mOAuthLoginInstance.getAccessToken(applicationContext)),2)
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
     * 네이버 로그인
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
        println("네이버 로그인 정보 $responseBody")
        return response
    }

    /**
     * 네이버 로그인
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

    /**
     * 네이버 로그인 라이브러리 애플리케이션 적용(네이버 로그인 인스턴스 초기화) 메서드
     */
    private fun naverLoginInit() {
        val naverClientId = getString(R.string.naver_login_id)
        val naverClientSecret = getString(R.string.naver_login_secret)
        val naverClientName = getString(R.string.naver_login_name)

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(this, naverClientId, naverClientSecret, naverClientName)

        // 네이버 로그인 버튼 클릭 시 로그인 핸들러 실행
        val buttonOAuthLoginImg = findViewById<OAuthLoginButton>(R.id.naverLoginButton)
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)
    }

    /**
     * 구글 로그인 인스턴스 초기화 메서드
     */
    private fun googleLoginInit() {

        // 구글 로그인 구성
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // 구글 로그인 관리 클래스
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 파이어베이스 통합 관리 객체 생성
        auth = FirebaseAuth.getInstance()

        // 구글 로그인 버튼 클릭 시 실행
        val googleLoginButton = findViewById<SignInButton>(R.id.googleLoginButton)
        googleLoginButton.setOnClickListener {
            // 구글 로그인 화면을 startActivityForResult로 열기
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
//            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }



    /**
     * 네이버 로그인
     * 로그인 성공했을 경우 메인화면으로 이동하는 메서드
     */
    fun successLogin() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * 구글 로그인
     * 구글 로그인 버튼 Text 변경
     */
    private fun setGoogleButtonText(loginButton: SignInButton, buttonText: String) {
        var i = 0
        while (i < loginButton.childCount) {
            var v = loginButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                return
            }
            i++
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}