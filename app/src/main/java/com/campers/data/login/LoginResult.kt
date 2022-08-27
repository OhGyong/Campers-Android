package com.campers.data.login

/**
 * 로그인
 */
data class SignInResult(
    val success: SignInResponse? = null,
    val failure: Exception? = null
)

/**
 * 회원가입
 */
data class SignUpResult(
    val success: SignUpResponse? = null,
    val failure: Exception? = null
)