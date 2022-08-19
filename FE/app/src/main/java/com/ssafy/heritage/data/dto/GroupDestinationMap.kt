package com.ssafy.heritage.data.dto

import com.google.gson.annotations.SerializedName

data class GroupDestinationMap(
    @SerializedName("heritageSeq") val heritageSeq: Int,
    @SerializedName("gdCompleted") val gdCompleted: Char,
    @SerializedName("heritage") val heritage: Heritage
)