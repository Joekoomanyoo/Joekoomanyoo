package com.ssafy.heritage.data.remote.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NearStampRequest (
    @SerializedName("userSeq") val userSeq: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String
) : Parcelable