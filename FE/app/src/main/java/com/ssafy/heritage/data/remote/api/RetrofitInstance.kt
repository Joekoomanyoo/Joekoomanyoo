package com.ssafy.heritage.data.remote.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.heritage.ApplicationClass.Companion.BASE_URL
import com.ssafy.heritage.util.NullOnEmptyConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

var VERIFY_DOMAIN: String = "*.lottois.info"

object RetrofitInstance {

    private const val CONNECT_TIMEOUT_SEC = 20000L

    //    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
//    // From https://www.washington.edu/itconnect/security/ca/load-der.crt
//    val path = "android.resource://" + "com.ssafy.heritage" + "/raw/" + "d102.cer";
////    val caInput: InputStream = BufferedInputStream(FileInputStream("d102.cer"))
//val caInput: InputStream = context.resources.openRawResource(R.raw.d102)
//
//    //val caInput: InputStream = BufferedInputStream(FileInputStream(path))
//    val ca: X509Certificate = caInput.use {
//        cf.generateCertificate(it) as X509Certificate
//    }
//
//    // Create a KeyStore containing our trusted CAs
//    val keyStoreType = KeyStore.getDefaultType()
//    val keyStore = KeyStore.getInstance(keyStoreType).apply {
//        load(null, null)
//        setCertificateEntry("ca", ca)
//    }
//
//
//
//    // 로깅인터셉터 세팅
    val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    //    // Create a TrustManager that trusts the CAs inputStream our KeyStore
//    val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
//    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
//        init(keyStore)
//    }
//
//    // Create an SSLContext that uses our TrustManager
//    val context: SSLContext = SSLContext.getInstance("TLS").apply {
//        init(null, tmf.trustManagers, null)
//    }
//
//    val hostnameVerifier = HostnameVerifier { _, session ->
//        HttpsURLConnection.getDefaultHostnameVerifier().run {
//            verify(VERIFY_DOMAIN, session)
//        }
//    }
    // OKHttpClient에 로깅인터셉터 등록
    val client =
        OkHttpClient.Builder()
        .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor())
            //.sslSocketFactory( context.socketFactory, tmf?.trustManagers?.get(0) as X509TrustManager)
            //.hostnameVerifier(hostnameVerifier)
            .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()


    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
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

    val feedApi: FeedService by lazy {
        retrofit.create(FeedService::class.java)
    }

    val fileApi: FileService by lazy {
        retrofit.create(FileService::class.java)
    }

    val ARApi: ARService by lazy {
        retrofit.create(ARService::class.java)
    }

//    private fun getTrustManagerFactory(context: Context): TrustManagerFactory? {
//        // 1. CA 로드
//        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
//        val caInput: InputStream = context.resources.openRawResource(R.raw.d102)
//        val ca: X509Certificate = caInput.use {
//            cf.generateCertificate(it) as X509Certificate
//        }
//
//        // 2. 신뢰할 수있는 CA를 포함하는 키 스토어 생성
//        val keyStoreType = KeyStore.getDefaultType()
//        val keyStore = KeyStore.getInstance(keyStoreType).apply {
//            load(null, null)
//            setCertificateEntry("ca", ca)
//        }
//
//        // 3. CA 입력을 신뢰하는 TrustManager 생성
//        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
//        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
//            init(keyStore)
//        }
//        return tmf
//    }
//    private fun getSSLSocketFactory(
//        tmf: TrustManagerFactory
//    ): SSLSocketFactory? {
//        //4. TrustManager를 사용하는 SSLContext 생성
//        val sslContext: SSLContext = SSLContext.getInstance("TLS")
//        sslContext.init(null, tmf.trustManagers, null)
//        return sslContext.socketFactory
//    }
}