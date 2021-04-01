package com.hadi.jweexample.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

object HttpClient {
    fun provideOkHttpClient(keyIdHeaderValue: String? = null): OkHttpClient {

        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(JWEInterceptor(keyIdHeaderValue = keyIdHeaderValue))
            .addInterceptor(httpLogging)

        return httpClient.build()
    }

}

class JWEInterceptor(private val keyIdHeaderValue: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "text/plain")
            .header("timestamp", Calendar.getInstance().timeInMillis.toString())
            .header(
                "ClientId",
                "K1g5K3hKR3ZWMG1pVGxLMDNKV2FtQT09WWFsbGFNb2JpbGUyMDIwOVRWb1I5dHBJMFdCTUFnemRlM3NNdz09"
            )
        // Add the keyId after first call in the session is done
        keyIdHeaderValue?.let { request.header("KeyId", it) }
        return chain.proceed(request.build())
    }
}