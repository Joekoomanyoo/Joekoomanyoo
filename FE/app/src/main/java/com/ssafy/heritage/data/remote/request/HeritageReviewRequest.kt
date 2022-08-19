package com.ssafy.heritage.data.remote.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ssafy.heritage.data.remote.response.HeritageReviewListResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeritageReviewRequest(
//    @SerializedName("heritageReviewSeq") val heritageReviewSeq: Int,                    // 문화유산 리뷰 번호
    @SerializedName("userSeq") val userSeq: Int,                                        // 사용자 정보
    @SerializedName("heritageSeq") val heritageSeq: Int,                                // 문화유산 번호
    @SerializedName("heritageReviewText") val heritageReviewText: String,               // 문화유산 리뷰 내용
//    @SerializedName("heritageReviewRegistedAt") val heritageReviewRegistedAt: String,   // 문화유산 리뷰 등록시간
    @SerializedName("reviewImgUrl") var reviewImgUrl: String = "",                                    // 첨부파일 번호
    @SerializedName("userNickname") val userNickname: String,                            // 사용자 닉네임
    @SerializedName("profileImgUrl") val profileImgUrl: String = ""                            // 사용자 프사
) : Parcelable