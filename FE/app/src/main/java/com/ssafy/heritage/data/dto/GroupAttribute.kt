package com.ssafy.heritage.data.dto

import com.google.gson.annotations.SerializedName

data class GroupAttribute(
    val groupSeq: Int,                          // 그룹 번호
    val gaSeq: Int,                             // 모임 속성 인덱스 번호
    var gaRegion: String,                       // 지역
    var gaStartDate: String,                    // 일정 시작일
    var gaEndDate: String,                      // 일정 종료일
    var gaChildJoin: Char,                      // 아이 동반 여부(Y, N)
    var gaGlobalJoin: Char,                     // 글로벌 모임 여부(Y, N)
    var gaAge: Int,                             // 나이대
  //  val gaCreatedAt: String,                    // 생성시간
  //  val gaUpdatedAt: String                     // 수정시간
)