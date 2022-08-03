package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    // 아이디 중복검사
    @GET("/api/user/check/email/{userId}")
    suspend fun checkEmail(@Path("userId") userId: String): Response<String>     // 이미 값이 있으면 false, 없으면 true

    // 닉네임 중복검사
    @GET("/api/user/check/nickname/{userNickname}")
    suspend fun checkNickname(@Path("userNickname") userNickname: String): Response<String>     // 이미 값이 있으면 false, 없으면 true

    // 이메일 인증하기
    @GET("/api/user/emailAuth/{userId}")
    suspend fun emailAuth(@Path("userId") userId: String): Response<String>

    // 일반 회원가입
    @POST("/api/user/signup")
    suspend fun signup(@Body user: User): Response<String>

    // 회원탈퇴
    @DELETE("/api/user/resign/{userId}")
    suspend fun resign(@Path("userId") userId: String): Response<String>

    // 일반 로그인
    @POST("/api/user/login")
    suspend fun login(@Body map: HashMap<String, String>): Response<String>

    // 소셜 아이디 중복 확인
    @GET("/api/user/social/checkId/{userId}")
    suspend fun socialCheckId(@Path("userId") userId: String): Response<String>     // 이미 값이 있으면 false, 없으면 true

    // 소셜 로그인
    @POST("/api/user/social/login")
    suspend fun socialLogin(@Body map: HashMap<String, String>): Response<String>

    // 소셜 회원가입
    @POST("/api/user/social/signup")
    suspend fun socialSignup(@Body user: User): Response<String>
}