package com.ssafy.heritage.util

import android.util.Base64
import android.util.Log
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.data.dto.User
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets.UTF_8

private const val TAG = "JWTUtils___"

object JWTUtils {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String): User? {
        try {
            Log.d(TAG, "decoded: $JWTEncoded")
            val split = JWTEncoded.split(".").toTypedArray()
            val body = getJson(split[1])
            Log.d(TAG, "decoded: $body")

            val userSeq = JSONObject(body)["userSeq"] as Int
            val userId = JSONObject(body)["userId"] as String
            val userNickname = JSONObject(body)["userNickname"] as String
            val userBirth = JSONObject(body)["userBirth"] as String
            val socialLoginType = JSONObject(body)["socialLoginType"] as String
            val userGender = (JSONObject(body)["userGender"] as String)[0]
            val profileImgUrl = JSONObject(body)["profileImgUrl"] as String

            val user = User(
                userSeq = userSeq,
                userId = userId,
                userNickname = userNickname,
                userPassword = null,
                userBirth = userBirth,
                socialLoginType = socialLoginType,
                userGender = userGender,
                profileImgUrl = profileImgUrl,
                fcmToken = "",
                userRegisteredAt = "",
                userUpdatedAt = "",
                isDeleted = ' '
            )

            return user
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "decoded: $e")
            ApplicationClass.sharedPreferencesUtil.deleteToken()
            return null
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, UTF_8)
    }
}