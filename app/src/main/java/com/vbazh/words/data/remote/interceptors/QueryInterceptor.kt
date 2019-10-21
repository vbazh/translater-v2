package com.vbazh.words.data.remote.interceptors

import com.vbazh.words.BuildConfig
import com.vbazh.words.data.DataConsts
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(DataConsts.API_KEY_QUERY_PARAMETER, BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}