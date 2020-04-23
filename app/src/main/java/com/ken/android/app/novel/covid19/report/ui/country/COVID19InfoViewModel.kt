package com.ken.android.app.novel.covid19.report.ui.country

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import com.ken.android.app.novel.covid19.report.repository.bean.COVID19ChartData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase

interface COVID19InfoViewModel {
    fun isLoading() : ObservableBoolean
    fun getGlobalCaseLiveData() : LiveData<GlobalTotalCase>
    fun getGlobalCaseErrorLiveData() : LiveData<String>
    fun getCountriesLiveData() : LiveData<List<Country>>
    fun getCountriesErrorLiveData() : LiveData<String>
    fun getCOVID19ChartLiveData() : LiveData<COVID19ChartData>
    fun searchErrorLiveData() : LiveData<String>
    fun loadGlobalTotalCase()
    fun loadCountries()
    fun loadCountries(sort : String)
    fun search(country: String)
    fun destroy()
}