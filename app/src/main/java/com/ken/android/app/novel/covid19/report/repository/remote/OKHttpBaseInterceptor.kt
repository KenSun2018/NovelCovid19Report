package com.ken.android.app.novel.covid19.report.repository.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * only normal
 * **/
class OKHttpBaseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}