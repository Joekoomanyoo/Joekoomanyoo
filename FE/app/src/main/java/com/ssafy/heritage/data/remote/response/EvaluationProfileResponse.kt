package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvaluationProfileResponse(
    @SerializedName("groupSeq") val groupSeq: Int,
    @SerializedName("profileImgUrl") val profileImgUrl: String,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("userSeq") val userSeq: Int
): Parcelable