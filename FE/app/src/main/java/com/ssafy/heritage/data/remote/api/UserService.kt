package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.*
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
    @PUT("/api/user/resign/{userId}")
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

    // 사용자 정보 불러오기
    @GET("/api/modify/{userSeq}")
    suspend fun getUserInfo(@Path("userSeq") userSeq: Int): Response<User>

   // 사용자 정보 수정하기
    @PUT("/api/modify")
    suspend fun modifyProfile(@Body userModify: UserModify): Response<String>

    // 사용자 비밀번호 확인하기
    @POST("/api/modify/check-password")
    suspend fun checkPassword(@Body map: HashMap<String, String>): Response<String>

    // 사용자 비밀번호 변경하기
    @PUT("/api/modify/find-password")
    suspend fun findPassword(@Body map: HashMap<String, String>): Response<String>

    // 문화유산 스크랩을 추가한다
    @POST("/api/heritage/scrap")
    suspend fun insertHeritageScrap(@Body scrap: HeritageScrap): Response<String>

    // 문화유산 스크랩 목록을 불러온다
    @GET("/api/mypage/scrap/{userSeq}")
    suspend fun selectAllScraps(@Path("userSeq") userSeq: Int): Response<List<Heritage>>

    // 문화유산 스크랩을 삭제한다
    @DELETE("/api/mypage/scrap/{userSeq}/{heritageSeq}")
    suspend fun deleteHeritageScrap(@Path("userSeq") userSeq: Int, @Path("heritageSeq") heritageSeq: Int): Response<String>

    // 내 키워드 리스트 불러온다
    @GET("/api/mypage/keyword/list/{userSeq}")
    suspend fun selectAllMyKeyword(@Path("userSeq") userSeq: Int): Response<List<Keyword>>

    // 내 키워드 생성한다
    @POST("/api/mypage/keyword")
    suspend fun insertMyKeyword(@Body body: Keyword): Response<String>

    // 내 키워드 삭제한다
    @DELETE("/api/mypage/keyword/list/{myKeywordSeq}")
    suspend fun deleteMyKeyword(@Path("myKeywordSeq") myKeywordSeq: Int): Response<String>

    // 내 모임의 목적지들을 조회한다
    @GET("/api/group/my-destination")
    suspend fun selectAllMyDestination(): Response<List<GroupDestinationMap>>

    // 내 일정 불러오기
    @GET("/api/mypage/schedule/{userSeq}/list")
    suspend fun selectAllMySchedule(@Path("userSeq") userSeq: Int): Response<List<Schedule>>

    // 내 일정 생성하기
    @POST("/api/mypage/schedule")
    suspend fun insertMySchedule(@Body schedule: Schedule): Response<String>

    // 내 일정 삭제하기
    @DELETE("/api/mypage/schedule/{myScheduleSeq}")
    suspend fun deleteMySchedule(@Path("myScheduleSeq") scheduleSeq: Int): Response<String>

    // 내 알림 설정 불러오기
    @GET("/api/push/{userSeq}")
    suspend fun getMyNotiSetting(@Path("userSeq") userSeq: Int): Response<String>

    // 내 알림 설정하기
    @PUT("/api/push/{userSeq}/{userSetting}/push-setting")
    suspend fun setMyNotiSetting(@Path("userSeq") userSeq: Int, @Path("userSetting") userSetting: Char): Response<String>

    // 내 알림 내역 리스트 불러오기
    @GET("/api/push/history/{userSeq}")
    suspend fun selectAllMyNoti(@Path("userSeq") userSeq: Int): Response<List<Noti>>

    // 토큰 서버에 보내기
    @POST("/api/push/token")
    suspend fun pushToken(@Body token: FCMToken): Response<String>

}