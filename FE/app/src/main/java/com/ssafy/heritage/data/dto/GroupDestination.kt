package com.ssafy.heritage.data.dto

data class GroupDestination(
    val gdSeq: Int,             // 번호
    val groupSeq: Int,          // 그룹번호
    val heritageSeq: Int,       // 문화유산번호
    val gdCompleted: Char       // 완료 여부
)