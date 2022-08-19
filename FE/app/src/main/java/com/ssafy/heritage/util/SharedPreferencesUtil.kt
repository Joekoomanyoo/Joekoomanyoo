package com.ssafy.heritage.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.request.NearStampRequest

class SharedPreferencesUtil(context: Context) {
    private val sharedPreferencesName = "store_preference"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    // 사용자 정보 저장
    fun saveUser(user: User){
        val editor = preferences.edit()
        user.userSeq?.let { editor.putInt("userSeq", it) }
        editor.putString("userNickName",user.userNickname)
        editor.commit()
    }

    // 사용자 정보 불러오기
    fun getUser(): Int {
        return preferences.getInt("userSeq", 0)
    }

    // 사용자 정보 불러오기
    fun getUserNickName(): String? {
        return preferences.getString("userNickName", "")
    }
    // 토큰 저장
    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString("token", token)
        editor.commit()
    }

    // 토큰 불러오기
    fun getToken(): String? {
        val token = preferences.getString("token", null)
        return token
    }

    // preference 지우기
    fun deleteToken() {
        val editor = preferences.edit()
        editor.remove("token")
        editor.apply()
    }

    fun saveStamp(stamp:Stamp){
        val editor = preferences.edit()
        editor.putString("heritageLat", stamp.heritageLat)
        editor.putString("heritageLng", stamp.heritageLng)
        editor.putInt("stampSeq", stamp.stampSeq)
        editor.putInt("heritageSeq", stamp.heritageSeq)
        editor.putString("stampUrl", stamp.stampImgUrl)
        editor.putString("stampTitle", stamp.stampTitle)
        editor.putString("stampCategory",stamp.stampCategory)
        editor.putString("heritageLocal", stamp.heritageLocal)
        editor.commit()
    }

    fun getStamp(): Stamp {
        val result = Stamp(
            preferences.getInt("stampSeq",0),
            preferences.getString("stampImgUrl", null).toString(),
            preferences.getString("stampTitle", null).toString(),
            "",
            preferences.getInt("heritageSeq",0),
            preferences.getString("heritageLocal",null).toString(),
            preferences.getString("heritageLng", null).toString(),
            preferences.getString("heritageLat", null).toString(),
            preferences.getString("stampCategory", null).toString(),
            'N'
        )
        return result
    }
    fun deleteStamp() {
        val editor = preferences.edit()
        editor.remove("stampSeq")
        editor.remove("stampImgUrl")
        editor.remove("stampTitle")
        editor.remove("heritageSeq")
        editor.remove("heritageLocal")
        editor.remove("heritageLng")
        editor.remove("heritageLat")
        editor.remove("stampCategory")

        editor.apply()
    }
}