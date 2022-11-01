package com.campers.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * retrofit 호출 시 공통으로 쓰이는 부분만 따로 정리.
 * 싱글톤 객체로 선언
 */
object CommonService {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000")
//        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

data class CommonArrayResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonArray
)

data class CommonArrayResult (
    val success: CommonArrayResponse? = null,
    val failure: Exception? = null
)

data class CommonObjectResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("payload")
    val payload: JsonObject
)

/**
 * 사용자 게시판 게시물 글쓰기, 사용자 게시판 게시물 댓글 작성
 */
data class CommonObjectResult (
    val success: CommonObjectResponse? = null,
    val failure: Exception? = null
)