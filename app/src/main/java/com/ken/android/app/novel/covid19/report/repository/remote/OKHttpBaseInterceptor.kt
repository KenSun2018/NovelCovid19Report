package com.ken.android.app.novel.covid19.report.repository.remote

import com.ken.android.app.novel.covid19.report.utils.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


/**
 * only normal
 * **/
class OKHttpBaseInterceptor @Inject constructor(): Interceptor {

    companion object{
        const val TAG = "Interceptor"
    }
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        Log.i(TAG, " send request ${original.url()}\nconnection = ${chain.connection()}\n headers = ${original.headers()}")



        val request = original.newBuilder()
            .method(original.method(), original.body())
            .build()
        val response =  chain.proceed(request)

        Log.d(TAG, "response header = ${response.headers()}")

        return response
    }
}