package com.ken.android.app.novel.covid19.report.ui.info

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.COVID19Repository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepositoryImpl

interface COVID19InfoViewModel {
    fun isLoading() : LiveData<Boolean>
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

    @VisibleForTesting
    fun setMockLoading(isLoading: MutableLiveData<Boolean>)



    open class RxFactory(private val covid19RxApiRepository : COVID19RxApiRepository) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return COVID19InfoViewModelRxImpl(covid19RxApiRepository) as T
        }
    }

    open class Factory(private val covid19Repository: COVID19Repository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return COVID19InfoViewModelImpl(covid19Repository) as T
        }
    }
}