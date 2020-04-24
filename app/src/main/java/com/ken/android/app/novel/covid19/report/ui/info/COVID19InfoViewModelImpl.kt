package com.ken.android.app.novel.covid19.report.ui.info

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.COVID19Repository
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor


class COVID19InfoViewModelImpl : ViewModel(), COVID19InfoViewModel {

    private var covid19Repository = COVID19Repository(OKHttpBaseInterceptor())

    private val globalTotalCaseLiveData : LiveData<GlobalTotalCase> by lazy {
        covid19Repository.globalTotalCaseLiveData
    }

    private val globalTotalCaseErrorLiveData : LiveData<String> by lazy {
        covid19Repository.globalTotalCaseErrorLiveData
    }

    private val isLoadingLazy: MutableLiveData<Boolean> by lazy {
        covid19Repository.isLoading
    }

    private val countriesLiveDataLazy : LiveData<List<Country>> by lazy {
        covid19Repository.countriesLiveData
    }

    private val countriesErrorLiveDataLazy : LiveData<String> by lazy {
        covid19Repository.countriesErrorLiveData
    }

    private val searchErrorLiveData = MutableLiveData<String>()

    private val covid19ChartLiveData = MutableLiveData<COVID19ChartData>()



    override fun loadGlobalTotalCase(){
        covid19Repository.loadGlobalTotalCase()
    }

    override fun loadCountries(){
        covid19Repository.loadCountries()
    }

    override fun loadCountries(sort : String){
        covid19Repository.loadCountriesBySorting(sort)
    }

    override fun search(country: String) {
        val countries = countriesLiveDataLazy.value

        if(countries == null){
            searchErrorLiveData.value = country
            return
        }

        var isSearchKeyWordMatchCountry = false
        for(countryObj in countries){
            if(countryObj.country == country){
                isSearchKeyWordMatchCountry = true
                break
            }
        }

        if(!isSearchKeyWordMatchCountry){
            searchErrorLiveData.value = country
            return
        }
        covid19Repository.search(country)
    }

    override fun destroy() {


    }

    override fun getGlobalCaseLiveData(): LiveData<GlobalTotalCase> {
        return globalTotalCaseLiveData
    }

    override fun getGlobalCaseErrorLiveData(): LiveData<String> {
        return globalTotalCaseErrorLiveData
    }

    override fun getCountriesLiveData(): LiveData<List<Country>> {
        return countriesLiveDataLazy
    }

    override fun getCountriesErrorLiveData(): LiveData<String> {
        return countriesErrorLiveDataLazy
    }

    override fun getCOVID19ChartLiveData(): LiveData<COVID19ChartData> {
        return covid19ChartLiveData
    }

    override fun searchErrorLiveData(): LiveData<String> {
        return searchErrorLiveData
    }

    override fun isLoading(): MutableLiveData<Boolean> {
        return isLoadingLazy
    }
}