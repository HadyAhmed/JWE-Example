package com.hadi.jweexample.network

import com.google.gson.Gson
import com.hadi.jweexample.model.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClientApi {
    @POST("CMS2/api/Login")
    fun login(@Body request: String): Call<ResponseBody>

    @GET("CMS2/api/GetAPIKeys")
    suspend fun getApiKey(): ResponseBody
}

object RetrofitClient {
    fun retrofitClient(keyIdHeaderValue: String? = null): ClientApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .baseUrl("https://grey.paysky.io:7006")
        .client(HttpClient.provideOkHttpClient(keyIdHeaderValue = keyIdHeaderValue))
        .build()
        .create(ClientApi::class.java)
}