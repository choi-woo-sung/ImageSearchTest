package com.woosung.data.di

import com.woosung.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                BuildConfig.API_KEY
            )
            .build()
        return chain.proceed(request)
    }
}