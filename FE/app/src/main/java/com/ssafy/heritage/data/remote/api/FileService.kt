package com.ssafy.heritage.data.remote.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface FileService {

    @Multipart
    @POST("/image/uploadFile")
    suspend fun saveImage(@Part file: MultipartBody.Part): Response<String>
}