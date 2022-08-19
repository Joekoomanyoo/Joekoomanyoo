package com.ssafy.heritage.data.dto

class FeedHashTag(
    val fhSeq: Int,                 // 해시태그 번호
    val feedSeq: Int,               // 사용자 번호
    val fhTag: String,              // 해시태그
    val fhCreatedAt: String,        // 해시태그 생성 날짜
)