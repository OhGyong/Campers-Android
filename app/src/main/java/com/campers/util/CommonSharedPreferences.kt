package com.campers.util

import android.content.Context

/**
 * accessToken과 refreshToken을 저장할 클래스
 */
class CommonSharedPreferences(context: Context) {

    /**
     * 매개변수는 환경 설정 파일 이름(name), 값을 검색하고 수정할 수 있는 mode
     */
    private val preferences = context.getSharedPreferences("SharedPref", 0)

    /**
     * get,set 메서드를 통해 accessToken을 관리
     */
    var accessToken: String?
        get() = preferences.getString("accessToken", null)
        set(accessTokenValue) = preferences.edit().putString("accessToken", accessTokenValue).apply()

    var refreshToken: String?
        get() = preferences.getString("refreshToken", null)
        set(pRefreshToken) = preferences.edit().putString("refreshToken", pRefreshToken).apply()


    /**
     * 회원 이름
     */
    var userName: String?
        get() = preferences.getString("name", null)
        set(pUserName) = preferences.edit().putString("name", pUserName).apply()

    /**
     * 로그아웃 했을때 토큰을 날리는 함수
     */
    fun clearPreferences() {
        preferences.edit().clear().apply()
    }
}