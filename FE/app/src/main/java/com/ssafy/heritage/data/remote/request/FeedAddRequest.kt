package com.ssafy.heritage.data.remote.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedAddRequest(
//    @SerializedName("feedSeq") val feedSeq: Int,               // 게시글 번호
    @SerializedName("userSeq") val userSeq: Int,               // 사용자 번호
    @SerializedName("feedImgUrl") val feedImgUrl: String,         // 피드 첨부파일 Url
    @SerializedName("feedTitle") val feedTitle: String,          // 제목
    @SerializedName("feedContent") val feedContent: String,        // 본문
    @SerializedName("feedOpen") val feedOpen: Char,             // 공개 여부 (N,Y)
    @SerializedName("hashtags") val hashtags: List<String>,             // 공개 여부 (N,Y)
//    @SerializedName("") val feedCreatedAt: String,      // 등록 시간
//    @SerializedName("") val feedUpdatedAt: String       // 수정 시간
) : Parcelable