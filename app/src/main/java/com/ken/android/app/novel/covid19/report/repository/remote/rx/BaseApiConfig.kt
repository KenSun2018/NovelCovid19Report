package com.ken.android.app.novel.covid19.report.repository.remote.rx

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//抽成 APIModule, 保留舊 code 參考用
@Deprecated(" move to [APIModule]")
open class BaseApiConfig (private var interceptor: Interceptor) {
    companion object{
        const val TIME_OUT_CONNECT = 30
        const val TIME_OUT_READ = 30
        const val TIME_OUT_WRITE = 30
    }

    protected var okHttpClient : OkHttpClient

    init {
        okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(TIME_OUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_WRITE.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun replaceInterceptor(interceptor: Interceptor){
        okHttpClient.interceptors().remove(this.interceptor)
        okHttpClient.interceptors().add(interceptor)
        this.interceptor = interceptor
    }
}