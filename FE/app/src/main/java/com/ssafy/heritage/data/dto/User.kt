package com.ssafy.heritage.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userSeq: Int?,              // 사용자 번호, 기본키
    val attachSeq: Int?,             // 첨부파일 번호
    val userId: String,             // 아이디(이메일 형식)
    var userNickname: String,       // 닉네임
    var userPassword: String?,      // 비밀번호
    var userBirth: String,          // 출생년도 월일
    var socialLoginType: String,    // 로그인타입
    var userGender: Char,               // 성별(M,F)
    var profileImgUrl: String,      // 프로필 사진 링크
    var jwtToken: String,           // jwt 토큰
    var fcmToken: String,           // fcm 토큰
    var userRegisteredAt: String,   // 가입 시간
    var userUpdatedAt: String,      // 수정 시간
    var isDeleted: Char,            // 회원 탈퇴 여부(N,Y)
) : Parcelable {
}
