package com.ssafy.heritage

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SharedPreferencesUtil

class ApplicationClass : Application() {

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil

        const val BASE_URL = "https://i7d102.p.ssafy.io"    // 서버 주소

    }

    override fun onCreate() {
        super.onCreate()

        initRepository()

        // shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(this)

        FirebaseApp.initializeApp(this)
    }

    private fun initRepository() {
        Repository.initialize(this)
    }
}