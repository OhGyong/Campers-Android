package com.example.campers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupWithNavController
import com.example.campers.ui.campingzone.CampingzoneFragment
import com.example.campers.ui.community.CommunityFragment
import com.example.campers.ui.home.HomeFragment
import com.example.campers.ui.mypage.MypageFragment
import com.example.campers.ui.notification.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val campingzoneFragment by lazy { CampingzoneFragment() }
    private val communityFragment by lazy { CommunityFragment() }
    private val notificationFragment by lazy { NotificationFragment() }
    private val mypageFragment by lazy { MypageFragment() }


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // 다크모드 비활성화
//        initNavigationBar()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fl_container) as NavHost
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bnv_main)
            .setupWithNavController(navController)
    }

//    private fun initNavigationBar() {
//        val bnvMain : BottomNavigationView = findViewById(R.id.bnv_main)
//        bnvMain.run {
//            setOnItemSelectedListener {
//                when (it.itemId) {
//                    R.id.homeFragment -> {
//                        changeFragment(homeFragment)
//                    }
//                    R.id.campingzoneFragment -> {
//                        changeFragment(campingzoneFragment)
//                    }
//                    R.id.communityFragment -> {
//                        changeFragment(communityFragment)
//                    }
//                    R.id.notificationFragment -> {
//                        changeFragment(notificationFragment)
//                    }
//                    R.id.mypageFragment -> {
//                        changeFragment(mypageFragment)
//                    }
//                }
//                true
//            }
//            selectedItemId = R.id.homeFragment
//        }
//    }
//
//    private fun changeFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_container, fragment).commit()
//    }
}