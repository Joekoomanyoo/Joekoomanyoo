package com.ssafy.heritage.data.dto

data class Group(
    val groupSeq: Int,              // 그룹 번호
    val attachSeq: Int,             // 커버 이미지 첨부파일 번호
    val groupName: String,          // 모임 이름
    val groupMaker: Int,            // 개설자
    val groupIntro: String,         // 모임 소개
    val groupAccessType: Char,      // 공개 설정(Y,N)
    val groupPwd: String,           // 비밀번호
    val groupIsActive: Char,        // 활성화 여부(Y,N) :
    val groupIsFull: Char,          // 모집 여부(Y,N)
    val groupCreatedAt: String,     // 생성시간
    val groupUpdatedAt: String,     // 수정시간
)