package com.campers.ui.splash

import android.app.Activity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.campers.R
import com.campers.ui.splash.adapter.AppIntroAdapter

class AppIntroActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = AppIntroAdapter(imageList())
    }

    private fun imageList(): ArrayList<Int>{
        return arrayListOf(R.drawable.intro1, R.drawable.intro2)
    }
}