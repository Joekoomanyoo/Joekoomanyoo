package com.ssafy.heritage.util

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.heritage.data.dto.User

class SharedPreferencesUtil(context: Context) {
    private val sharedPreferencesName = "store_preference"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    // 정보 저장
    fun save(){}

    // 정보 불러오기
    fun get(){}

    // preference 지우기
    fun deleteUser(){
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}