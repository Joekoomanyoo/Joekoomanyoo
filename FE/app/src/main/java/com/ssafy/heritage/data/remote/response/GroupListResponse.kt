package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupListResponse(

    @SerializedName("groupSeq") val groupSeq: Int,                              // 그룹번호
    @SerializedName("attachSeq") val attachSeq: Int,                            // 커버 이미지 첨부파일 번호
    @SerializedName("groupName") val groupName: String,                         // 모임이름
    @SerializedName("groupMaker") val groupMaker: Int,                          // 개설자
    @SerializedName("groupDescription") val groupDescription: String,           // 모임 소개
    @SerializedName("groupAccessType") val groupAccessType: Char,               // 공개 설정
    @SerializedName("groupPassword") val groupPwd: String,                      // 비밀번호
    @SerializedName("groupIsActive") val groupIsActive: Char,
    @SerializedName("groupStatus") val groupStatus: String,
    @SerializedName("groupCreatedAt") val groupCreatedAt: String,               // 생성 시간
    @SerializedName("groupUpdatedAt") val groupUpdatedAt: String,               // 수정 시간
    @SerializedName("groupMaxCount") val groupMaxCount: Int

) : Parcelable