package com.ssafy.heritage.data.dto

data class GroupDailyMemo(
    val groupSeq: Int,              // 그룹 번호
    val gdmSeq: Int,                // 데일리메모 인덱스
    val gdmDate: String,            // 일정 날짜
    val gdmContent: String,         // 메모 내용
    val gdmCreatedAt: String,       // 등록 시간
    val gdmUpdatedAt: String        // 수정 시간
)