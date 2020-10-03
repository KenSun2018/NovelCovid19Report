package com.ken.android.app.novel.covid19.report.di.api

import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiService
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiService
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiService
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




    @Provides
    fun provideNewsApiOrgRxApiService(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient

    ) : NewsApiOrgRxApiService {
        val retrofit = getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://newsapi.org/")
            .build()
        return retrofit.create(NewsApiOrgRxApiService::class.java)
    }


    @Provides
    fun provideCOVID19RxApiService(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient
    ) : COVID19RxApiService {
        val retrofit = getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://corona.lmao.ninja/v2/")
            .build()
        return retrofit.create(COVID19RxApiService::class.java)
    }



    @Provides
    fun provideTaiwanMaskRxApiService(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient : OkHttpClient
    ) : TaiwanMaskRxApiService{
        val retrofit = getBaseRetrofitBuilder(gsonConverterFactory, rxJava2CallAdapterFactory, okHttpClient)
            .baseUrl("https://raw.githubusercontent.com/")
            .build()
        return retrofit.create(TaiwanMaskRxApiService::class.java)
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