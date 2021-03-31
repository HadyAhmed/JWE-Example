package com.hadi.jweexample.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object HttpClient {
    fun provideOkHttpClient(): OkHttpClient {

        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(JWEInterceptor())
            .addInterceptor(httpLogging)

        return httpClient.build()
    }

}

class JWEInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        JWEUtils().makeEncryptionOfJson(JSONObject())

        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept-Language", "ar")
        return chain.proceed(request.build())
    }

}