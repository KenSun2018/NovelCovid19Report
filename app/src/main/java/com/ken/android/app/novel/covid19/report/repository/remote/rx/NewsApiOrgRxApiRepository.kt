package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.di.api.NewsApiService
import com.ken.android.app.novel.covid19.report.repository.bean.News
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class NewsApiOrgRxApiRepository @Inject constructor(@NewsApiService retrofit: Retrofit) : NewsApiOrgRxApiService {

//    companion object{
//        private const val TAG = "NewsApiOrgRepository"
//        private const val BASE_URL = "https://newsapi.org/"
//
//    }
    private var apiService : NewsApiOrgRxApiService = retrofit.create(NewsApiOrgRxApiService::class.java)

    override fun getTopHeadline(q: String, country: String, apiKey: String): Single<News> {
        return apiService.getTopHeadline(q, country, apiKey)
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}