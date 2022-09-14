package com.campers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupWithNavController
import com.campers.ui.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fl_container) as NavHost
        navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bnv_main)
            .setupWithNavController(navController)
    }

    /**
     * 뒤로가기 버튼 이벤트 처리
     */
    override fun onBackPressed() {
        // 홈 화면이 아니면 뒤로가기로 상위 navigation으로 이동
        if(navController.currentDestination?.label != "HomeFragment"){
            navController.navigateUp()
        }
        // 홈 화면이면 뒤로 가기 두번눌렀을 때 앱을 종료하도록 설정
        else{
            if (System.currentTimeMillis() - waitTime >= 1500) {
                waitTime = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
            } else {
                finish() // 액티비티 종료
            }
        }
    }
}