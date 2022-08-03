package com.ssafy.heritage.data.dto

data class HeritageReview(
    val heritageReviewSeq: Int,             // 문화유산 리뷰 번호
    val userSeq: Int,                       // 사용자 정보
    val heritageSeq: Int,                   // 문화유산 번호
    val heritageReviewText: String,         // 문화유산 리뷰 내용
    val heritageReviewRegistedAt: Int,      // 문화유산 리뷰 등록시간
    val attachSeq: Int,                     // 첨부파일 번호
)