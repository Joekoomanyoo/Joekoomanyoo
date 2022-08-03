package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeritageReviewListResponse(
    @SerializedName("heritageReviewSeq") val heritageReviewSeq: Int,                // 문화유산 리뷰 번호
    @SerializedName("userSeq") val userSeq: Int,                                    // 사용자 정보
    @SerializedName("heritageSeq") val heritageSeq: Int,                            // 문화유산 번호
    @SerializedName("heritageReviewText") val heritageReviewText: String,           // 문화유산 리뷰 내용
    @SerializedName("heritageReviewRegistedAt") val heritageReviewRegistedAt: Int,  // 문화유산 리뷰 등록시간
    @SerializedName("attachSeq") val attachSeq: Int,                                // 첨부파일 번호
) : Parcelable