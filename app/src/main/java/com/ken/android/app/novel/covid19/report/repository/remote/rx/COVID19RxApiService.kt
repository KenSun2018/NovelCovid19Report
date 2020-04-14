package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * base url
 * https://corona.lmao.ninja/
 *
 * https://corona.lmao.ninja/all
 *
 * country sorting
 * https://corona.lmao.ninja/countries?sort=cases
 * https://corona.lmao.ninja/countries?sort=cases
 *
 * search
 * https://corona.lmao.ninja/countries/:country
 * https://corona.lmao.ninja/countries/taiwan
 * **/
interface COVID19RxApiService {

    @GET("all")
    fun getGlobalTotalCase() : Single<GlobalTotalCase>

    @GET("countries")
    fun getCountries() : Single<List<Country>>

    @GET("countries")
    fun getCountries(@Query("sort") country : String) : Single<List<Country>>


    @GET("countries/{country}")
    fun searchCountry(@Path("country") country:String) : Single<Country>
}