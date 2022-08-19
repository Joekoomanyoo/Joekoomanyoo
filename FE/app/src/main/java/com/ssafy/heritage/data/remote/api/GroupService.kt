package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.data.dto.Chat
import com.ssafy.heritage.data.dto.GroupDestinationMap
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.*
import com.ssafy.heritage.data.remote.response.EvaluationProfileResponse
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import retrofit2.Response
import retrofit2.http.*

interface GroupService {

    //모임 관리 API
    // 새로운 모임을 개설한다
    @POST("/api/group/add")
    suspend fun insertGroup(@Body body: GroupAddRequest): Response<GroupListResponse>

    // 모임 목록을 조회한다
    @GET("/api/group/list")
    suspend fun selectAllGroups(): Response<List<GroupListResponse>>

    // 모임을 삭제한다
    @DELETE("/api/group/{groupSeq}/delete")
    suspend fun deleteGroup(@Path("groupSeq") groupSeq: Int): Response<Boolean>

    // 모임 정보를 수정한다
    @PUT("/api/group/{groupSeq}/modify")
    suspend fun modifyGroup(@Path("groupSeq") groupSeq: Int, @Body body: GroupListResponse): Response<GroupListResponse>

    // 모임 정보 상세 조회한다
    @GET("/api/group/{groupSeq}/info")
    suspend fun selectGroupDetail(@Path("groupSeq") groupSeq: Int): Response<GroupListResponse>

    // 나의 모임 정보만 조회한다
    @GET("/api/group/my-group")
    suspend fun selectMyGroups(): Response<List<MyGroupResponse>>


    // 모임 상태 변경 - R(모집), O(진행), F(종료)
    @PUT("/api/group/{groupSeq}/status")
    suspend fun modifyStatus(@Path("groupSeq") groupSeq: Int, @Body body: HashMap<String, String>): Response<String>

    // 모임 활성화 여부를 등록한다
    @PUT("/api/group/{groupSeq}/active")
    suspend fun changeGroupActiveState(@Body body: Int): Response<Boolean>

    // 가입을 승인한다
    @PUT("/api/group/{groupSeq}/member/approve/{userSeq}")
    suspend fun approveGroupJoin(@Path("groupSeq") groupSeq: Int, @Path("userSeq") userSeq: Int): Response<Boolean>

    // 가입을 신청한다
    @POST("/api/group/{groupSeq}/member/join")
    suspend fun applyGroupJoin(@Path("groupSeq") groupSeq: Int, @Body body: GroupJoin): Response<Boolean>

    // 가입을 탈퇴/취소
    @DELETE("/api/group/{groupSeq}/member/leave")
    suspend fun leaveGroupJoin(@Path("groupSeq") groupSeq: Int): Response<Boolean>

    // 가입 거절/퇴장
    @DELETE("/api/group/{groupSeq}/member/out/{userSeq}")
    suspend fun outGroupJoin(@Path("groupSeq") groupSeq: Int, @Path("userSeq") userSeq: Int): Response<Boolean>

    // 회원 목록을 조회한다
    @GET("/api/group/{groupSeq}/member/list")
    suspend fun selectGroupMembers(@Path("groupSeq") groupSeq: Int): Response<List<Member>>



    // 그룹별로 상호평가 가져오기
    @GET("/api/mypage/eval/{userSeq}/{groupSeq}")
    suspend fun selectGroupEvaluation(@Path("groupSeq") groupSeq: Int, @Path("userSeq") userSeq: Int): Response<List<EvaluationProfileResponse>>

    // 상호평가 결과 전송
    @POST("/api/mypage/eval")
    suspend fun insertGroupEvaluation(@Body body :List<EvaluationRequest>): Response<String>


    // 모임 일정을 등록한다
    @POST("/api/group/{groupSeq}/schedule/add")
    suspend fun insertGroupSchedule(@Path("groupSeq") groupSeq: Int, @Body body: GroupSchedule): Response<String>

    // 모임 일정을 삭제한다
    @DELETE("/api/group/{groupSeq}/schedule/delete")
    suspend fun deleteGroupSchedule(@Path("groupSeq") groupSeq: Int, @Query("gsSeq") gsSeq: Int): Response<String>

    // 모임 일정을 조회한다
    @GET("/api/group/{groupSeq}/schedule/list")
    suspend fun selectGroupSchedule(@Path("groupSeq") groupSeq: Int) : Response<List<GroupSchedule>>


    // 모임 목적지 조회
    @GET("/api/group/{groupSeq}/destination/list")
    suspend fun getGroupDestination(@Path("groupSeq") groupSeq: Int): Response<List<GroupDestinationMap>>

    // 모임 목적지를 등록한다
    @POST("/api/group/{groupSeq}/destination/add")
    suspend fun insertGroupDestination(@Path("groupSeq") groupSeq: Int, @Query("heritageSeq") heritageSeq: Int): Response<String>

    // 모임 목적지를 삭제한다
    @DELETE("/api/group/{groupSeq}/destination/delete")
    suspend fun deleteGroupDestination(@Path("groupSeq") groupSeq: Int, @Query("heritageSeq") heritageSeq: Int): Response<String>

    // 모임 목적지 방문 여부를 등록한다




    // 전체 채팅을 불러온다
    @GET("/chat/message/{groupSeq}")
    suspend fun selectAllChat(@Path("groupSeq") groupSeq: Int) : Response<List<Chat>>

    // 그룹 사진 변경
    @PUT("/api/group/{groupSeq}/modify/image")
    suspend fun updateGroupimage(@Path("groupSeq") groupSeq: Int, @Query("groupImgUrl") groupImgUrl: String) : Response<String>
}