package com.ssafy.heritage.data.dto

import com.google.gson.annotations.SerializedName

data class GroupAttribute(
    val groupSeq: Int,                          // 그룹 번호
    val gaSeq: Int,                             // 모임 속성 인덱스 번호
    val gaMax: Int,                             // 최대 인원
    val gaRegion: String,                       // 지역
    val gaStartDate: String,                    // 일정 시작일
    val gaEndDate: String,                      // 일정 종료일
    val gaChildJoin: Char,                      // 아이 동반 여부
    val gaGlobalJoin: Char,                     // 글로벌 모임 여부
    val gaAge: Int,                             // 나이대
    val gaCreatedAt: String,                    // 생성시간
    val gaUpdatedAt: String                     // 수정시간
)