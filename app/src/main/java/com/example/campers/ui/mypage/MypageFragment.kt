package com.example.campers.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.campers.R
import com.example.campers.util.SharedPreferences

class MypageFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        // 로그아웃 시 accessToken 삭제
        logoutButton.setOnClickListener {
            SharedPreferences(view.context).clearPreferences()
        }
    }
}