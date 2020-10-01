package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.News
import io.reactivex.Single
import javax.inject.Inject

class NewsApiOrgRxApiRepository @Inject constructor(var apiService : NewsApiOrgRxApiService) : NewsApiOrgRxApiService {

    override fun getTopHeadline(q: String, country: String, apiKey: String): Single<News> {
        return apiService.getTopHeadline(q, country, apiKey)
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}