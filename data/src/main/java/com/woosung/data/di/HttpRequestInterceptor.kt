package com.woosung.data.di

import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK 48809a065a684fbbb9060b8acbbbf557"
            )
            .build()
        return chain.proceed(request)
    }
}