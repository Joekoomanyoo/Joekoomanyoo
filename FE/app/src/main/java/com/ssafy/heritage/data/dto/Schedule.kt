package com.ssafy.heritage.data.dto

data class Schedule(
    val myScheduleSeq: Int = 0,
    val userSeq: Int,
    val myScheduleContent: String,
    val myScheduleDate: String,
    val myScheduleRegistedAt: String = "",
    val myScheduleUpdatedAt: String = "",
)