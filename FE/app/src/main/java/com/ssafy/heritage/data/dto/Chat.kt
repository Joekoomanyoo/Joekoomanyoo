package com.ssafy.heritage.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val chatSeq: Int = 0,
    val groupSeq: Int,
    val userSeq: Int,
    val chatContent: String = "",   // 메시지 내용
    val chatImgUrl: String = "",
    val sender: String = "",    // 메시지 보낸 사람
    val userImg: String = "",   // 유저 프로필 사진
    val createdTime: String = ""
)