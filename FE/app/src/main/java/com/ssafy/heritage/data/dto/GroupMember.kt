package com.ssafy.heritage.data.dto

data class GroupMember(
    val memberSeq: Int,                 // 인덱스
    val userSeq: Int,                   // 아이디
    val groupSeq: Int,                  // 그룹 번호
    val memberStatus: Int,              // 가입 상태(0: 가입 대기, 1: 일반, 2: 방장)
    val memberInAt: String,             // 가입 승인 시간
    val memberJoinAppeal: String,       // 자기 소개
    val memberEval: Char,               // 평가 여부(N,Y)
    val memberCreatedAt: String,        // 등록 시간
    val memberUpdatedAt: String         // 수정 시간

)