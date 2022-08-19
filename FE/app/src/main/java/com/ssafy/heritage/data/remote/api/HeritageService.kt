package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.data.dto.HeritageScrap
import com.ssafy.heritage.data.remote.request.HeritageReviewRequest
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface HeritageService {

    // 전체 문화유산 목록 가져온다
    @GET("/api/heritage/list")
    suspend fun selectAllHeritage(): Response<List<Heritage>>

    // 해당 문화유산의 전체 리뷰를 가져온다
    @GET("/api/heritage/reviews/{heritageSeq}")
    suspend fun selectAllHeritageReviews(@Path("heritageSeq") heritageSeq: Int): Response<List<HeritageReviewListResponse>>

    // 리뷰를 작성한다
    @POST("/api/heritage/review")
    suspend fun insertHeritageReview(@Body body: HeritageReviewRequest): Response<String>

    // 리뷰를 삭제한다
    @DELETE("/api/heritage/review/{heritageReviewSeq}/{heritageSeq}")
    suspend fun deleteHeritageReivew(@Path("heritageReviewSeq") heritageReviewSeq: Int, @Path("heritageSeq") heritageSeq: Int): Response<String>


    // 스터디에 공유한다

    // 문화유산 정렬해서 져오기??
    // 문화유산 상세정보 가져오기??

//    @Multipart
//    @POST("/uploadFile")
//    suspend fun saveImage(@Part file: MultipartBody.Part): Response<Boolean>

    // 문화유산 본인 위치 기준으로 가까운순 정렬
    @POST("/api/heritage/heritage-info")
    suspend fun orderByLocation(@Body map: HashMap<String, String>): Response<List<Heritage>>

    // 문화유산 카테고리 정렬
    @POST("/api/heritage/heritage-info/sort")
    suspend fun getOrderHeritage(@Body map: HashMap<String, Any>): Response<List<Heritage>>
}