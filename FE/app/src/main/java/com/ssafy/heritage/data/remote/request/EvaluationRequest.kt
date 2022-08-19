package com.ssafy.heritage.data.remote.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EvaluationRequest(
    @SerializedName("userSeq")val userSeq : Int,                        // 사용자 번호
    @SerializedName("userReceivedSeq") val userReceivedSeq : Int,       // 평가 받는 유저 번호
    @SerializedName("groupSeq") val groupSeq: Int,                      // 그룹 번호
    @SerializedName("evalCnt") val evalCnt: Int = 0,                    // 상호평가 횟수
    @SerializedName("evalList1") var evalList1: Int,                    // 1번 상호평과 결과
    @SerializedName("evalList2") val evalList2: Int,                    // 2번 상호평과 결과
    @SerializedName("evalList3") val evalList3: Int,                    // 3번 상호평과 결과
    @SerializedName("evalList4") val evalList4: Int,                    // 4번 상호평과 결과
    @SerializedName("evalList5") val evalList5: Int                     // 5번 상호평과 결과
): Parcelable