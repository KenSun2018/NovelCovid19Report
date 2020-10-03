package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import io.reactivex.Single

interface COVID19RxApiRepository{
    fun getGlobalTotalCase() : Single<GlobalTotalCase>
    fun getCountries() : Single<List<Country>>
    fun getCountries(country : String) : Single<List<Country>>
    fun searchCountry(country:String) : Single<Country>
}