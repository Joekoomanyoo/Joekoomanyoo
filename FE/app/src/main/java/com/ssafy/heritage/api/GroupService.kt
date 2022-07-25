package com.ssafy.heritage.api

import com.ssafy.heritage.data.response.GroupListResponse
import retrofit2.Response
import retrofit2.http.*

interface GroupService {

    // 새로운 모임을 개설한다
    @POST("/api/group")
    suspend fun insertGroup(@Body body: GroupListResponse): Response<Boolean>

    // 모임 목록을 조회한다
    @GET("/api/group")
    suspend fun selectAllGroup(): Response<List<GroupListResponse>>

    // 모임을 삭제한다
    @DELETE("/api/group/{groupSeq}")
    suspend fun deleteGroup(@Path("groupSeq") groupSeq: Int): Response<Boolean>

    // 모임 활성화 여부를 등록한다
    @PUT("/api/group/{groupSeq}/active")
    suspend fun changeGroupActiveState()

    // 모임 상세정보를 조회한다
    @GET("/api/group/{groupSeq}/attribute")
    suspend fun selectGroupDetail()

    // 모임 정보를 수정한다
    @PUT("/api/group/{groupSeq}/modify")

    // 나의 모임 정보만 조회한다
    @GET("/api/group/{userSeq}/my")



    // 가입을 승인한다

    // 가입을 거절한다

    // 가입을 취소한다

    // 가입을 탈퇴한다

    // 회원을 강제퇴장 시킨다

    // 회원 목록을 조회한다

    // 회원 프로필을 조회한다

    // 회원 평가를 조회한다

    // 회원을 평가한다

    // 모임 일정을 등록한다

    // 모임 일정을 수정한다

    // 모임 일정을 삭제한다

    // 모임 일정을 조회한다

    // 모임 데일리 메모를 등록한다

    // 모임 데일리 메모를 조회한다

    // 모임 데일리 메모를 수정한다

    // 모임 데일리 메모를 삭제한다

    // 모임 목적지를 등록한다

    // 모임 목적지를 삭제한다

    // 모임 목적지 방문 여부를 등록한다

    // 채팅을 불러온다

    // 채팅을 입력한다

    // 채팅을 삭제한다

}