package com.ssafy.heritage.data.remote.api

import com.ssafy.heritage.ApplicationClass.Companion.BASE_URL
import com.ssafy.heritage.util.NullOnEmptyConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val CONNECT_TIMEOUT_SEC = 20000L

    // 로깅인터셉터 세팅
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    // OKHttpClient에 로깅인터셉터 등록
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // 인터페이스를 사용한 인스턴스 설정
    val groupApi: GroupService by lazy {
        retrofit.create(GroupService::class.java)
    }

    val userApi: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val heritageApi: HeritageService by lazy {
        retrofit.create(HeritageService::class.java)
    }
}