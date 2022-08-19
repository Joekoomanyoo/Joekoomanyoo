package com.ssafy.heritage.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class FeedListResponse(
    @SerializedName("feedSeq") val feedSeq: Int,                    // 게시글 번호
    @SerializedName("userSeq") val userSeq: Int,               // 사용자 번호
    @SerializedName("feedImgUrl") var feedImgUrl: String = "",      // 피드 첨부파일 Url
    @SerializedName("feedTitle") val feedTitle: String,             // 제목
    @SerializedName("feedContent") val feedContent: String,         // 본문
    @SerializedName("feedOpen") val feedOpen: Char,                 // 공개 여부 (N,Y)
    @SerializedName("userImgUrl") val userImgUrl: String,           // 사용자 프사
    @SerializedName("userNickname") val userNickname: String,       // 사용자 닉네임
    @SerializedName("hashtag") val hashtag: Array<String>,       // 사용자 닉네임
    @SerializedName("createdTime") val createdTime: String,     // 등록 시간
    @SerializedName("userLike") var userLike: Char,                 // 해당 사용자가 좋아요를 눌렀는가?? (N,Y)
//    @SerializedName("feedUpdatedAt") val feedUpdatedAt: String       // 수정 시간
) : Parcelable, Serializable {
    // 해시코드 충돌 해결
    override fun hashCode(): Int {
        var result = feedSeq.hashCode()
        if (feedImgUrl.isNullOrEmpty()) {
            result = 31 * result + feedImgUrl.hashCode()
        }
        return result
    }
}