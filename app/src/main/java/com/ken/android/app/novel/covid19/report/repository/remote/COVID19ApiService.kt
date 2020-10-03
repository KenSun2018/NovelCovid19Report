package com.ken.android.app.novel.covid19.report.repository.remote

import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 保留參考用，無使用，改用 Rx
 * **/

@Deprecated("replace with Rx")
interface COVID19ApiService {

    @GET("all")
    fun getGlobalTotalCase() : Call<GlobalTotalCase>

    @GET("countries")
    fun getCountries() : Call<List<Country>>

    @GET("countries")
    fun getCountries(@Query("sort") country : String) : Call<List<Country>>


    @GET("countries/{country}")
    fun searchCountry(@Path("country") country:String) : Call<Country>
}