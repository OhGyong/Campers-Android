package com.campers.util

import org.json.JSONObject

class CommonObject {
    companion object {

        /**
         * 로그인 회원 정보 Json 데이터
         */
        var LoginJsonData = JSONObject()

        /**
         * 소셜 플랫폼 ID
         * 1 == 구글, 2 == 네이버
         */
        var socialPlatform = 0

    }
}