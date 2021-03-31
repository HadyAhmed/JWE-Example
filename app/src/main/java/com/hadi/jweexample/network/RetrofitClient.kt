package com.hadi.jweexample.network

import com.google.gson.Gson
import com.hadi.jweexample.model.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ClientApi {
    @POST("CMS2/api/Login")
    suspend fun login(@Body request: LoginRequest): ResponseBody


}

object RetrofitClient {
    val retrofitClient: ClientApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl("https://grey.paysky.io:7006")
        .client(HttpClient.provideOkHttpClient())
        .build()
        .create(ClientApi::class.java)
}