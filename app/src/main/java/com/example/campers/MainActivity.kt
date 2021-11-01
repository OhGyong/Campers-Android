package com.example.campers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        val bnvMain : BottomNavigationView = findViewById(R.id.bnv_main)
        bnvMain.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.first -> {
                        changeFragment(homeFragment)
                    }
                    R.id.second -> {
                        changeFragment(campingzoneFragment)
                    }
                    R.id.third -> {
                        changeFragment(communityFragment)
                    }
                    R.id.fourth -> {
                        changeFragment(notificationFragment)
                    }
                    R.id.fifth -> {
                        changeFragment(mypageFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.first
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, fragment).commit()
    }
}