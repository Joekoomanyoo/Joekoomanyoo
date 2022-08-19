package com.ssafy.heritage.data.remote.request

data class GroupSchedule(
    val gsSeq: Int = 0,
    val groupSeq: Int = 0,
    val gsDateTime: String,           // 날짜
    val gsContent: String,         // 내용
    val gsRegisteredAt: String = "",
    val gsUpdatedAt: String = ""
)