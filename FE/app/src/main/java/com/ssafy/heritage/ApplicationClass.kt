package com.ssafy.heritage

import android.app.Application
import android.content.Context
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SharedPreferencesUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class ApplicationClass : Application() {

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil

        // 주소
        const val BASE_URL = "http://i7d102.p.ssafy.io:8081"

    }
    override fun onCreate() {
        super.onCreate()

        initRepository()

        // shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(this)
    }

    private fun initRepository() {
        Repository.initialize(this)
    }
}