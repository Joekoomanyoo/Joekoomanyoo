package com.ssafy.heritage.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModify(
    val userSeq: Int,
    var userNickname: String,
    var userPassword: String?,
    var userBirth: String,
    var userGender: Char,
    var profileImgUrl: String
) : Parcelable {
}
