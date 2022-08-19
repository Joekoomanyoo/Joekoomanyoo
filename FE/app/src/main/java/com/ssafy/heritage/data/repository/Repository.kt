package com.ssafy.heritage.data.repository

import android.content.Context
import com.ssafy.heritage.data.dto.*
import com.ssafy.heritage.data.remote.api.RetrofitInstance.ARApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.feedApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.fileApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.groupApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.heritageApi
import com.ssafy.heritage.data.remote.api.RetrofitInstance.userApi
import com.ssafy.heritage.data.remote.request.*
import com.ssafy.heritage.data.remote.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

class Repository constructor(context: Context) {

    // group
    suspend fun insertGroup(body: GroupAddRequest): Response<GroupListResponse> =
        groupApi.insertGroup(body)

    suspend fun selectAllGroups(): Response<List<GroupListResponse>> = groupApi.selectAllGroups()
    suspend fun deleteGroup(groupSeq: Int): Response<Boolean> = groupApi.deleteGroup(groupSeq)
    suspend fun changeGroupActiveState(body: Int): Response<Boolean> =
        groupApi.changeGroupActiveState(body)

    suspend fun selectGroupMembers(groupSeq: Int): Response<List<Member>> =
        groupApi.selectGroupMembers(groupSeq)

    suspend fun selectGroupDetail(groupSeq: Int): Response<GroupListResponse> =
        groupApi.selectGroupDetail(groupSeq)

    suspend fun modifyGroup(groupSeq: Int, body: GroupListResponse): Response<GroupListResponse> =
        groupApi.modifyGroup(groupSeq, body)

    // group - 가입/신청/승인/취소/탈퇴
    suspend fun approveGroupJoin(groupSeq: Int, userSeq: Int): Response<Boolean> =
        groupApi.approveGroupJoin(groupSeq, userSeq)

    suspend fun applyGroupJoin(groupSeq: Int, body: GroupJoin): Response<Boolean> =
        groupApi.applyGroupJoin(groupSeq, body)

    suspend fun leaveGroupJoin(groupSeq: Int): Response<Boolean> =
        groupApi.leaveGroupJoin(groupSeq)

    suspend fun outGroupJoin(groupSeq:Int, userSeq: Int): Response<Boolean> =
        groupApi.outGroupJoin(groupSeq, userSeq)

    suspend fun modifyStatus(groupSeq: Int, body: HashMap<String, String>): Response<String> = groupApi.modifyStatus(groupSeq, body)


    // group - 내 그룹 조회
    suspend fun selectMyGroups(): Response<List<MyGroupResponse>> =
        groupApi.selectMyGroups()

    // group -  목적지
    suspend fun getGroupDestination(groupSeq: Int): Response<List<GroupDestinationMap>> = groupApi.getGroupDestination(groupSeq)

    suspend fun insertGroupDestination(groupSeq: Int, heritageSeq: Int): Response<String> =
        groupApi.insertGroupDestination(groupSeq, heritageSeq)

    suspend fun deleteGroupDestination(groupSeq: Int, heritageSeq: Int): Response<String> =
        groupApi.deleteGroupDestination(groupSeq, heritageSeq)

    // group - 일정
    suspend fun insertGroupSchedule(groupSeq: Int, body: GroupSchedule): Response<String> =
        groupApi.insertGroupSchedule(groupSeq, body)

    suspend fun deleteGroupSchedule(groupSeq: Int, gsSeq: Int): Response<String> =
        groupApi.deleteGroupSchedule(groupSeq, gsSeq)

    suspend fun selectGroupSchedule(groupSeq: Int): Response<List<GroupSchedule>> =
        groupApi.selectGroupSchedule(groupSeq)

    suspend fun selectAllChat(groupSeq: Int): Response<List<Chat>> =
        groupApi.selectAllChat(groupSeq)

    suspend fun updateGroupimage(groupSeq: Int, groupImgUrl: String) : Response<String> = groupApi.updateGroupimage(groupSeq, groupImgUrl)

    // group - 상호평가
    suspend fun selectGroupEvaluation(groupSeq: Int, userSeq: Int): Response<List<EvaluationProfileResponse>> =
        groupApi.selectGroupEvaluation(groupSeq, userSeq)

    suspend fun insertGroupEvaluation(body :List<EvaluationRequest>): Response<String> =
        groupApi.insertGroupEvaluation(body)


    // user
    suspend fun checkEmail(userId: String): Response<String> = userApi.checkEmail(userId)
    suspend fun checkNickname(userNickname: String): Response<String> =
        userApi.checkNickname(userNickname)

    suspend fun signup(user: User): Response<String> = userApi.signup(user)
    suspend fun login(map: HashMap<String, String>): Response<String> = userApi.login(map)
    suspend fun emailAuth(userId: String): Response<String> = userApi.emailAuth(userId)

    suspend fun socialCheckId(userId: String): Response<String> = userApi.socialCheckId(userId)
    suspend fun socialLogin(map: HashMap<String, String>): Response<String> =
        userApi.socialLogin(map)

    suspend fun socialSignup(user: User): Response<String> = userApi.socialSignup(user)

    suspend fun getUserInfo(userSeq: Int): Response<User> = userApi.getUserInfo(userSeq)

    suspend fun resign(userId: String): Response<String> = userApi.resign(userId)

    suspend fun modifyProfile(userModify: UserModify): Response<String> =
        userApi.modifyProfile(userModify)

    suspend fun checkPassword(map: HashMap<String, String>): Response<String> =
        userApi.checkPassword(map)

    suspend fun findPassword(@Body map: HashMap<String, String>): Response<String> =
        userApi.findPassword(map)

    suspend fun insertHeritageScrap(scrap: HeritageScrap): Response<String> =
        userApi.insertHeritageScrap(scrap)

    suspend fun selectAllScraps(userSeq: Int): Response<List<Heritage>> =
        userApi.selectAllScraps(userSeq)

    suspend fun deleteHeritageScrap(userSeq: Int, heritageSeq: Int): Response<String> =
        userApi.deleteHeritageScrap(userSeq, heritageSeq)

    suspend fun selectAllMyKeyword(userSeq: Int): Response<List<Keyword>> =
        userApi.selectAllMyKeyword(userSeq)

    suspend fun insertMyKeyword(body: Keyword): Response<String> = userApi.insertMyKeyword(body)
    suspend fun deleteMyKeyword(myKeywordSeq: Int): Response<String> =
        userApi.deleteMyKeyword(myKeywordSeq)

    suspend fun selectAllMyDestination(): Response<List<GroupDestinationMap>> =
        userApi.selectAllMyDestination()

    suspend fun selectAllMySchedule(userSeq: Int): Response<List<Schedule>> =
        userApi.selectAllMySchedule(userSeq)

    suspend fun insertMySchedule(schedule: Schedule): Response<String> =
        userApi.insertMySchedule(schedule)

    suspend fun deleteMySchedule(scheduleSeq: Int): Response<String> =
        userApi.deleteMySchedule(scheduleSeq)

    suspend fun getMyNotiSetting(userSeq: Int): Response<String> = userApi.getMyNotiSetting(userSeq)
    suspend fun setMyNotiSetting(userSeq: Int, userSetting: Char): Response<String> =
        userApi.setMyNotiSetting(userSeq, userSetting)

    suspend fun selectAllMyNoti(userSeq: Int): Response<List<Noti>> =
        userApi.selectAllMyNoti(userSeq)

    suspend fun pushToken(token: FCMToken): Response<String> = userApi.pushToken(token)

    // heritage
    suspend fun selectAllHeritage(): Response<List<Heritage>> = heritageApi.selectAllHeritage()
    suspend fun insertHeritageReview(body: HeritageReviewRequest): Response<String> =
        heritageApi.insertHeritageReview(body)

    suspend fun selectAllHeritageReviews(heritageSeq: Int): Response<List<HeritageReviewListResponse>> =
        heritageApi.selectAllHeritageReviews(heritageSeq)

    suspend fun deleteHeritageReview(heritageReviewSeq: Int, heritageSeq: Int): Response<String> =
        heritageApi.deleteHeritageReivew(heritageReviewSeq, heritageSeq)

    suspend fun orderByLocation(map: HashMap<String, String>): Response<List<Heritage>> =
        heritageApi.orderByLocation(map)

    suspend fun getOrderHeritage(@Body map: HashMap<String, Any>): Response<List<Heritage>> =
        heritageApi.getOrderHeritage(map)


    // feed
    suspend fun selectMyFeeds(): Response<List<FeedListResponse>> =
        feedApi.selectMyFeeds()

    suspend fun selectFeedsByHashtag(fhTag: String): Response<List<FeedListResponse>> =
        feedApi.selectFeedsByHashtag(fhTag)

    suspend fun selectAllFeeds(): Response<List<FeedListResponse>> =
        feedApi.selectAllFeeds()

    suspend fun insertFeed(body: FeedAddRequest): Response<FeedListResponse> =
        feedApi.insertFeed(body)

    suspend fun selectFeedDetail(feedSeq: Int): Response<FeedListResponse> =
        feedApi.selectFeedDetail(feedSeq)

    suspend fun deleteFeed(feedSeq: Int): Response<Boolean> =
        feedApi.deleteFeed(feedSeq)

    suspend fun selectFeedHashTag(fhTag: String): Response<FeedListResponse> =
        feedApi.selectFeedHashTag(fhTag)

    suspend fun changeFeedOpen(feedSeq: Int, feedOpen: Char): Response<String> =
        feedApi.changeFeedOpen(feedSeq, feedOpen)

    suspend fun insertFeedLike(feedSeq: Int): Response<String> =
        feedApi.insertFeedLike(feedSeq)

    suspend fun countFeedLike(feedSeq: Int): Response<Int> =
        feedApi.countFeedLike(feedSeq)

    suspend fun deleteFeedLike(feedSeq: Int): Response<String> =
        feedApi.deleteFeedLike(feedSeq)

    // file
    suspend fun sendImage(file: MultipartBody.Part): Response<String> =
        fileApi.saveImage(file)

    // stamp
    suspend fun selectAllStamp(): Response<List<Stamp>> = ARApi.selectAllStamp()
    suspend fun getMyStamp(userSeq: Int): Response<List<Stamp>> = ARApi.getMyStamp(userSeq)
    suspend fun addStamp(userSeq: Int, stampSeq: Int): Response<String> = ARApi.addStamp(userSeq, stampSeq)
    suspend fun selectStampCategory(): Response<List<StampCategory>> = ARApi.selectStampCategory()
    suspend fun selectNearStamp(location: NearStampRequest): Response<List<Stamp>> = ARApi.selectNearStamp(location)
    suspend fun selectMyStampCategory(userSeq: Int, categorySeq: Int): Response<List<StampCategory>> = ARApi.selectMyStampCategory(userSeq, categorySeq)
    suspend fun selectStampRank():Response<List<StampRankResponse>> = ARApi.selectStampRank()

    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}