package com.ken.android.app.novel.covid19.report.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.addTo
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.ui.BaseRxViewModel
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import javax.inject.Inject


open class COVID19InfoViewModelRxImpl @Inject constructor(private val covid19RxApiRepository: COVID19RxApiRepository) : BaseRxViewModel(), COVID19InfoViewModel {



    companion object{
        const val TAG = "COVID19ViewModel";
    }

    private val globalTotalCaseLiveData = MutableLiveData<GlobalTotalCase>()
    private val globalTotalCaseErrorLiveData = MutableLiveData<String>()
    private val countriesLiveData = MutableLiveData<List<Country>>()
    private val countriesErrorLiveData = MutableLiveData<String>()
    private val searchErrorLiveData = MutableLiveData<String>()
    private val covid19ChartLiveData = MutableLiveData<COVID19ChartData>()

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun getGlobalCaseLiveData(): LiveData<GlobalTotalCase> {
        return globalTotalCaseLiveData
    }

    override fun getGlobalCaseErrorLiveData(): LiveData<String> {
        return globalTotalCaseErrorLiveData
    }

    override fun getCountriesLiveData(): LiveData<List<Country>> {
        return countriesLiveData
    }

    override fun getCountriesErrorLiveData(): LiveData<String> {
        return countriesErrorLiveData
    }

    override fun getCOVID19ChartLiveData(): LiveData<COVID19ChartData> {
        return covid19ChartLiveData
    }

    override fun searchErrorLiveData(): LiveData<String> {
        return searchErrorLiveData
    }


    override fun loadGlobalTotalCase(){
        isLoading.value = true
        covid19RxApiRepository.getGlobalTotalCase()
            .doFinally{
                isLoading.value = false
            }
            .subscribe({ globalTotalCase ->

            globalTotalCaseLiveData.value = globalTotalCase

        }, { t ->

            globalTotalCaseErrorLiveData.value = "${t.message}"
        }).addTo(compositeDisposable)
    }


    override fun loadCountries(){
        isLoading.value = true
        covid19RxApiRepository.getCountries("deaths")
            .doFinally{
                isLoading.value = false
            }
            .subscribe({ countries ->

                covid19ChartLiveData.value = COVID19ChartData(countries)
                countriesLiveData.value = countries

        }, { t ->

            countriesErrorLiveData.value = "${t.message}"

        }).addTo(compositeDisposable)
    }

    override fun loadCountries(sort : String){
        isLoading.value = true
        covid19RxApiRepository.getCountries(sort)
            .doFinally{
                isLoading.value = false
            }
            .subscribe({ countries ->

            countriesLiveData.value = countries
        }, { t ->

            countriesErrorLiveData.value = "${t.message}"

        }).addTo(compositeDisposable)

    }

    override fun search(country: String) {

        val countries = countriesLiveData.value
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


        isLoading.value = true
        covid19RxApiRepository.searchCountry(country)
            .doFinally{
                isLoading.value = false
            }
            .subscribe({ it ->
            val responseCountries = ArrayList<Country>()
            responseCountries.add(it)
            countriesLiveData.value = responseCountries

        }, { t ->

            countriesErrorLiveData.value = "${t.message}"
        }).addTo(compositeDisposable)

    }




}