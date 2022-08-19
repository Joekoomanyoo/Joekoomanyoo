package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.data.remote.request.NearStampRequest
import com.ssafy.heritage.data.remote.response.StampRankResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ARService {

    // 전체 스탬프 리스트를 조회
    @GET("/api/ar/stamp/list")
    suspend fun selectAllStamp(): Response<List<Stamp>>

    // 나의 스탬프 리스트를 조회
    @GET("/api/mypage/stamp/{userSeq}")
    suspend fun getMyStamp(@Path("userSeq") userSeq: Int): Response<List<Stamp>>

    // 스탬프 나의 목록에 추가하기
    @POST("/api/ar/stamp/{userSeq}/{stampSeq}")
    suspend fun addStamp(@Path("userSeq") userSeq: Int, @Path("stampSeq") stampSeq: Int): Response<String>

    // 스탬프 카테고리별 개수 조회
    @GET("/api/ar/stamp/category")
    suspend fun selectStampCategory(): Response<List<StampCategory>>

    // 나의 스탬프 카테고리별 개수 조회
    @GET("/api/ar/stamp/category/{userSeq}")
    suspend fun selectMyStampCategory(@Path("userSeq") userSeq: Int, @Path("categorySeq") categorySeq: Int): Response<List<StampCategory>>

    // 내 위치 전송하여 나와 가까운 스탬프 정보 조회
    @POST("api/ar/location")
    suspend fun selectNearStamp(@Body location: NearStampRequest): Response<List<Stamp>>

    // 전체 사용자의 스탬프 순위를 가져온다
    @GET("/api/ar/stamp/rank")
    suspend fun selectStampRank():Response<List<StampRankResponse>>
}
