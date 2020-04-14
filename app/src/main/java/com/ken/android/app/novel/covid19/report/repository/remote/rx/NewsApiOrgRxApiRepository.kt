package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.News
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NewsApiOrgRxApiRepository(interceptor: Interceptor) : BaseApiConfig(interceptor), NewsApiOrgRxApiService {

    companion object{
        private const val TAG = "NewsApiOrgRepository"
        private const val BASE_URL = "https://newsapi.org/"

    }
    private var apiService : NewsApiOrgRxApiService

    init {

        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(NewsApiOrgRxApiService::class.java)
    }

    override fun getTopHeadline(q: String, country: String, apiKey: String): Single<News> {
        return apiService.getTopHeadline(q, country, apiKey)
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}