package com.ssafy.heritage.data.dto

data class StampCategory(
    val categoryCnt: Int,
    val categoryName: String,
    val categorySeq: Int,
    var myCnt: Int = 0
) {
    fun add() {
        myCnt += 1
    }
}