package com.ssafy.heritage.data.remote.api

import android.os.Build.VERSION_CODES.N
import com.ssafy.heritage.data.dto.Feed
import com.ssafy.heritage.data.dto.FeedHashTag
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.response.FeedListResponse
import retrofit2.Response
import retrofit2.http.*

interface FeedService {
    // 피드 API
    // 내 피드 조회
    @GET("/api/feed/my-feed")
    suspend fun selectMyFeeds(): Response<List<FeedListResponse>>

    // 피드 조회 by 해시태그
    @GET("/api/feed/list-by-hashtag/{fhTag}")
    suspend fun selectFeedsByHashtag(@Path("fhTag") fhTag: String): Response<List<FeedListResponse>>

    // 피드 전체 조회
    @GET("/api/feed/list")
    suspend fun selectAllFeeds(): Response<List<FeedListResponse>>

    // 피드 등록
    @POST("/api/feed/add")
    suspend fun insertFeed(@Body body: FeedAddRequest): Response<FeedListResponse>

    // 피드 수정
    // 피드 보기
    @GET("/api/feed/{feedSeq)/info")
    suspend fun selectFeedDetail(@Path("feedSeq") feedSeq: Int): Response<FeedListResponse>

    // 피드 삭제
    @DELETE("/api/feed/{feedSeq}/delete")
    suspend fun deleteFeed(@Path("feedSeq") feedSeq: Int): Response<Boolean>

    // 피드 활성화 여부 (Y: 공개, N: 비공개)
    @PUT("/api/feed/{feedSeq}/active/{feedOpen}")
    suspend fun changeFeedOpen(@Path("feedSeq") feedSeq: Int, @Path("feedOpen") feedOpen: Char): Response<String>

    // 피드 좋아요 API
    // 피드 좋아요 등록
    @POST("/api/feed/{feedSeq}/like/add")
    suspend fun insertFeedLike(@Path("feedSeq") feedSeq: Int): Response<String>

    // 피드 좋아요 개수 조회
    @GET("/api/feed/{feedSeq}/like/count")
    suspend fun countFeedLike(@Path("feedSeq") feedSeq: Int): Response<Int>

    // 피드 좋아요 해제
    @DELETE("/api/feed/{feedSeq}/like/delete")
    suspend fun deleteFeedLike(@Path("feedSeq") feedSeq: Int): Response<String>

    // 피드 해시태그 API
    // 피드 해시태그 목록 조회
    @GET("/api/feed/list-by-hashtag/{fhTag}")
    suspend fun selectFeedHashTag(@Path("fhTag") fhTag: String): Response<FeedListResponse>

    // 피드 해시태그 삭제
}