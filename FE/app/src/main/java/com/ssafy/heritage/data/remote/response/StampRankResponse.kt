package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StampRankResponse(
    @SerializedName("myStampCnt") val myStampCnt : Int,
    @SerializedName("userNickname") val userNickname : String,
    @SerializedName("userRank") val userRank: Int,
    @SerializedName("profileImgUrl") val profileImgUrl: String
) : Parcelable