package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import io.reactivex.Single
import javax.inject.Inject

class COVID19RxApiRepository @Inject constructor(var apiService : COVID19RxApiService) : COVID19RxApiService {


    override fun getGlobalTotalCase(): Single<GlobalTotalCase> {
        return apiService.getGlobalTotalCase()
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }

    override fun getCountries(): Single<List<Country>> {
        return apiService.getCountries()
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }

    override fun getCountries(country: String): Single<List<Country>> {
        return apiService.getCountries(country)
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }

    override fun searchCountry(country: String): Single<Country> {
        return apiService.searchCountry(country)
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}