package com.ken.android.app.novel.covid19.report.di

import com.ken.android.app.novel.covid19.report.di.qualifier.COVID19Service
import com.ken.android.app.novel.covid19.report.di.qualifier.NewsApiService
import com.ken.android.app.novel.covid19.report.di.qualifier.TWMaskService
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
open class APIModule {
    companion object{
        const val TIME_OUT_CONNECT = 30
        const val TIME_OUT_READ = 30
        const val TIME_OUT_WRITE = 30
    }

    @Provides
    fun provideInterceptor() : Interceptor{
        return newOkHttpBaseInterceptor()
    }

    protected fun newOkHttpBaseInterceptor() : Interceptor{
        return OKHttpBaseInterceptor()
    }


    @Provides
    fun provideOkHttpClientBuilder(interceptor: Interceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(TIME_OUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_WRITE.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
    }


    @Provides
    fun provideRxJava2CallAdapterFactory (): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }


    @NewsApiService
    @Provides
    fun provideNewsApiRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient
    ) : Retrofit {
        return getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://newsapi.org/")
            .build()
    }



    @COVID19Service
    @Provides
    fun provideCovid19ApiRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient
    ) : Retrofit {
        return getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://corona.lmao.ninja/v2/")
            .build()
    }

    @TWMaskService
    @Provides
    fun provideTWMaskApiRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient
    ) : Retrofit {

        return getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://raw.githubusercontent.com/")
            .build()
    }

    private fun getBaseRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient) :Retrofit.Builder{

        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
    }

}