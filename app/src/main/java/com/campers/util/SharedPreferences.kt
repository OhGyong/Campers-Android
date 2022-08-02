package com.campers.util

import android.content.Context

/**
 * accessToken을 저장할 클래스
 */
class SharedPreferences(context: Context) {

    /**
     * 매개변수는 환경 설정 파일 이름(name), 값을 검색하고 수정할 수 있는 mode
     */
    private val preferences = context.getSharedPreferences("Campers", 0)

    /**
     * get,set 메서드를 통해 accessToken을 관리
     */
    var accessToken: String?
        get() = preferences.getString("accessToken", null)
        set(accessTokenValue) = preferences.edit().putString("accessToken", accessTokenValue)
            .apply()


    /**
     * 로그아웃 했을때 토큰을 날리는 함수
     */
    fun clearPreferences() {
        preferences.edit().clear().apply()
    }
}