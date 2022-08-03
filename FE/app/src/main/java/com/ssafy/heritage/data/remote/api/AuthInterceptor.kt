package com.ssafy.heritage.data.remote.api

import android.util.Log
import com.ssafy.heritage.ApplicationClass
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "AuthInterceptor___"

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ApplicationClass.sharedPreferencesUtil.getToken() ?: ""

        Log.d(TAG, "intercept: $token")

        var req =
            chain.request().newBuilder().addHeader(
                "Authorization",
                token
            ).build()
        return chain.proceed(req)
    }
}