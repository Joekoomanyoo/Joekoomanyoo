package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GroupListResponse(
    @SerializedName("groupSeq") var groupSeq: Int,                          // 그룹 번호
    @SerializedName("groupName") var groupName: String,                     // 그룹 이름
    @SerializedName("groupImgUrl") var groupImgUrl: String,               // 이미지
    @SerializedName("groupMaster") var groupMaster: String,                 // 그룹 방장
    @SerializedName("groupDescription") var groupDescription: String,       // 그룹 설명
    @SerializedName("groupAccessType") var groupAccessType: Char,           // 공개:Y, 비공개:N
    @SerializedName("groupPassword") var groupPassword: String,             // 그룹 비밀번호
    @SerializedName("groupMaxCount") var groupMaxCount: Int,                // 그룹 최대 인원
    @SerializedName("groupRegion") var groupRegion: String,                 // 그룹 관심 지역
    @SerializedName("groupStartDate") var groupStartDate: String,             // 일정 시작 날짜
    @SerializedName("groupEndDate") var groupEndDate: String,                 // 일정 종료 날짜
    @SerializedName("groupAgeRange") var groupAgeRange: Int,                // 나이대
    @SerializedName("groupWithChild") var groupWithChild: Char,             // 아이 동반 여부
    @SerializedName("groupWithGlobal") var groupWithGlobal: Char,           // 글로벌 여부
    @SerializedName("groupActive") var groupActive: Char,                   // 활성:Y, 비활성:N
    @SerializedName("groupStatus") var groupStatus: Char,                   // 모집중:R, 진행중:O, 종료:F
//    @SerializedName("createdTime") var createdTime: Date,
//    @SerializedName("updatedTime") var updatedTime: Date
) : Parcelable