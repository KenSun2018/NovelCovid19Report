package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class COVID19RxApiRepository(interceptor: Interceptor) : BaseApiConfig(interceptor), COVID19RxApiService {

    companion object{
        private const val TAG = "COVID19RxApiRepository"
        private const val BASE_URL = "https://corona.lmao.ninja/"
    }


    private var apiService : COVID19RxApiService

    init {
        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(COVID19RxApiService::class.java)
    }


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