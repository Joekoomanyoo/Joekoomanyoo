package com.ssafy.heritage.data.dto

data class Noti(
    val pushSeq: Int,
    val userSeq: Int,
    val pushTitle: String,
    val pushContent: String,
    val pushCreatedAt: String
)