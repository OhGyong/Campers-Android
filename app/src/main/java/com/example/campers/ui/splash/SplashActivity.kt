package com.example.campers.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.campers.MainActivity
import com.example.campers.R
import com.example.campers.util.SharedPreferences


/**
 * 스플래시 화면
 * accessToken을 확인하여 값이 있으면 메인화면으로 이동하고 없으면 로그인화면으로 이동
 */
class SplashActivity : Activity() {

    private val duration: Long = 5000 // 5초후에 액티비티 이동

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashLogoGif = findViewById<ImageView>(R.id.splash_logo_gif)
        Glide.with(this).load(R.raw.splash_gif).into(splashLogoGif)

        // accessToken이 없으면 앱 인트로 화면으로 이동
        if (SharedPreferences(this@SplashActivity).accessToken == null) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, AppIntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, duration)
        }
        // accessToken 값이 있으면(null이 아니면) 메인화면으로 이동
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, duration)
        }

    }
}