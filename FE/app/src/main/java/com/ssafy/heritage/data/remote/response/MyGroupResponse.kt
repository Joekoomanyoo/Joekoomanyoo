package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyGroupResponse(
    @SerializedName("groupSeq") var groupSeq: Int,
    @SerializedName("groupName") var groupName: String,
    @SerializedName("groupMaster") var groupMaster: String,
    @SerializedName("groupDescription") var groupDescription: String,
    @SerializedName("groupImgUrl") var groupImgUrl: String = "",
    @SerializedName("groupAccessType") var groupAccessType: Char,
    @SerializedName("groupPassword") var groupPassword: String,
    @SerializedName("groupMaxCount") var groupMaxCount: Int,
    @SerializedName("groupRegion") var groupRegion: String,
    @SerializedName("groupStartDate") var groupStartDate: String,
    @SerializedName("groupEndDate") var groupEndDate: String,
    @SerializedName("groupAgeRange") var groupAgeRange: Int,
    @SerializedName("groupWithChild") var groupWithChild: Char,
    @SerializedName("groupWithGlobal") var groupWithGlobal: Char,
    @SerializedName("groupActive") var groupActive: Char,
    @SerializedName("groupStatus") var groupStatus: Char,
    @SerializedName("memberStatus") var memberStatus: Int = 0,
    @SerializedName("memberIsEvaluated") var memberIsEvaluated: Char = 'N'
) : Parcelable