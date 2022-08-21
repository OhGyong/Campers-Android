package com.campers.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.campers.MainActivity
import com.campers.R
import com.campers.databinding.ActivityLoginBinding
import com.campers.ui.BaseActivity
import com.campers.util.CommonBottomSheetDialog
import com.campers.util.CommonInputDialog
import com.campers.util.CommonObject.Companion.LoginJsonData
import com.campers.util.CommonObject.Companion.socialPlatform
import com.campers.util.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.nhn.android.naverlogin.OAuthLogin
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * 소셜 로그인
 */
class LoginActivity : BaseActivity() {

    // 네이버 로그인 설정
    private lateinit var naverLoginInstance: OAuthLogin

    // 구글 로그인 설정
    private lateinit var googleSignInClient: GoogleSignInClient

    // 서버에서 받아온 accessToken
    private lateinit var userAccessToken: String

    private lateinit var mBinding: ActivityLoginBinding
    val mViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.login = this

        // 구글 로그인 인스턴스 초기화
        googleLoginInit()

        // 네이버 로그인 라이브러리 애플리케이션 적용(네이버 로그인 인스턴스 초기화)
        naverLoginInit()

        observerLiveData()
    }

    private fun observerLiveData() {
        // 로그인 처리
        mViewModel.signInData.observe(this, Observer {
            if(it.failure != null){
                // 로그인 상태에서 로그인이 호출되었을 때, 다른 기기에서 로그인 중입니다가 case일 수 도?
                if(it.failure.toString() == getString(R.string.sign_up_call)){

                    CommonBottomSheetDialog.Builder(this)
                        .setTitle(getString(R.string.check))
                        .setContent(getString(R.string.sign_in_status_another_device))
                        .setCheckBtn()
                        .show()
                }else{
                    CommonBottomSheetDialog.Builder(this)
                        .setTitle(getString(R.string.check))
                        .setContent(getString(R.string.sign_in_error))
                        .setCheckBtn()
                        .show()
                }
                return@Observer
            }

            val success = it.success

            if(success == null){
                CommonBottomSheetDialog.Builder(this)
                    .setTitle(getString(R.string.check))
                    .setContent(getString(R.string.sign_in_error))
                    .setCheckBtn()
                    .show()
                return@Observer
            }

            // 회원가입 호출
            if(success.status == 301){
                CommonInputDialog.Builder(this)
                    .setTitle(getString(R.string.sign_up))
                    .setEditText(getString(R.string.sign_up_input_nickname_hint))
                    .setCancelBtn()
                    .setCheckBtn(object: CommonInputDialog.BtnClickListener{
                        override fun onBtnClick(dialog: CommonInputDialog, content: String) {
                            LoginJsonData.put("name", content)
                            showLoading(this@LoginActivity)
                            mViewModel.getSignUpData(LoginJsonData, socialPlatform)
                            dialog.dismiss()
                        }
                    })
                    .show()
            }
            // 로그인 성공
            else{
                println(success)
                userAccessToken = success.data.get("accessToken").toString()
                SharedPreferences(this@LoginActivity).accessToken = userAccessToken
                successLogin()
            }
        })

        // 회원가입 처리
        mViewModel.signUpData.observe(this, Observer {
            hideLoading()

            if(it.failure != null) {
                CommonBottomSheetDialog.Builder(this)
                    .setTitle("확인")
                    .setContent(getString(R.string.sign_up_error))
                    .setCheckBtn()
                    .show()
            }

            val success = it.success

            if(success == null){
                CommonBottomSheetDialog.Builder(this)
                    .setTitle("확인")
                    .setContent(getString(R.string.sign_up_error))
                    .setCheckBtn()
                    .show()
                return@Observer
            }

            userAccessToken = success.data.get("accessToken").toString()
            SharedPreferences(this@LoginActivity).accessToken = userAccessToken
            successLogin()
        })
    }

    /**
     * 구글 로그인
     * 구글 로그인 인스턴스 초기화 메서드
     */
    @DelicateCoroutinesApi
    private fun googleLoginInit() {
        // 구글 로그인 버튼 텍스트 변경
        setGoogleButtonText(mBinding.btnGoogleLogin, getString(R.string.google_login_btn_text))

        // 구글 로그인 구성
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_web_client_id))
            .requestEmail()
            .build()

        // 구글 로그인 관리 클래스
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 구글 로그인 버튼 클릭 시 실행
        mBinding.btnGoogleLogin.setOnClickListener {
            socialPlatform = 1
            googleResultListener.launch(googleSignInClient.signInIntent)
        }
    }

    /**
     * 구글 로그인
     * startActivityForeResult 대신 사용. 전역변수로 사용해야 가능
     * googleSignInClient.signInIntent로 입력된 데이터를 받고 실행하는 변수
     */
    @DelicateCoroutinesApi
    private val googleResultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    GoogleLogin().firebaseAuthWithGoogle(account, mViewModel)
                } catch (error: ApiException) {
                    println("onActivity 실패 $error")
                }
            }
        }

    /**
     * 구글 로그인
     * 구글 로그인 버튼 Text 변경
     */
    private fun setGoogleButtonText(loginButton: SignInButton, buttonText: String) {
        for(i in 0..loginButton.childCount){
            val btnWord = loginButton.getChildAt(i)
            if (btnWord is TextView) {
                btnWord.text = buttonText
                return
            }
        }
    }

    /**
     * 네이버 로그인
     * 네이버 로그인 라이브러리 애플리케이션 적용(네이버 로그인 인스턴스 초기화) 메서드
     */
    private fun naverLoginInit() {
        val naverClientId = getString(R.string.naver_login_id)
        val naverClientSecret = getString(R.string.naver_login_secret)
        val naverClientName = getString(R.string.naver_login_name)

        naverLoginInstance = OAuthLogin.getInstance()
        naverLoginInstance.init(this, naverClientId, naverClientSecret, naverClientName)

        // 네이버 로그인 버튼 클릭 시 로그인 핸들러 실행
        mBinding.btnNaverLogin.setOAuthLoginHandler(
            NaverLogin().naverLoginHandler(this, mViewModel, naverLoginInstance)
        )
    }

    /**
     * 로그인 성공했을 경우 메인화면으로 이동하는 메서드
     */
    private fun successLogin() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}