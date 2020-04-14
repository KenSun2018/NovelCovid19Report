package com.ken.android.app.novel.covid19.report.ui.country

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import io.reactivex.disposables.CompositeDisposable


open class COVID19ViewModelRxImpl() : ViewModel(), COVID19ViewModel {

    private var covid19RxApiRepository =  COVID19RxApiRepository(OKHttpBaseInterceptor())

    private var disposables = CompositeDisposable()

    private var isLoading = ObservableBoolean(false)
    private val globalTotalCaseLiveData = MutableLiveData<GlobalTotalCase>()
    private val globalTotalCaseErrorLiveData = MutableLiveData<String>()
    private val countriesLiveData = MutableLiveData<List<Country>>()
    private val countriesErrorLiveData = MutableLiveData<String>()
    private val searchErrorLiveData = MutableLiveData<String>()

    @VisibleForTesting
    fun setMockRepository(covid19RxApiRepository: COVID19RxApiRepository){
        this.covid19RxApiRepository = covid19RxApiRepository
    }

    @VisibleForTesting
    fun setMockLoading(observableBoolean: ObservableBoolean){
        this.isLoading = observableBoolean
    }
    override fun isLoading(): ObservableBoolean {
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

    override fun searchErrorLiveData(): LiveData<String> {
        return searchErrorLiveData
    }


    override fun loadGlobalTotalCase(){
        isLoading.set(true)
        val disposable = covid19RxApiRepository.getGlobalTotalCase()
            .subscribe({ globalTotalCase ->
            isLoading.set(false)
            globalTotalCaseLiveData.value = globalTotalCase

        }, { t ->
            isLoading.set(false)
            globalTotalCaseErrorLiveData.value = "${t.message}"
        })

        disposables.add(disposable)

    }

    override fun loadCountries(){
        isLoading.set(true)
        val disposable = covid19RxApiRepository.getCountries("deaths")
            .subscribe({ countries ->
            isLoading.set(false)
            countriesLiveData.value = countries

        }, { t ->
            isLoading.set(false)
            countriesErrorLiveData.value = "${t.message}"

        })

        disposables.add(disposable)
    }

    override fun loadCountries(sort : String){
        isLoading.set(true)
        val disposable = covid19RxApiRepository.getCountries(sort)
            .subscribe({ countries ->
            isLoading.set(false)
            countriesLiveData.value = countries
        }, { t ->
            isLoading.set(false)
            countriesErrorLiveData.value = "${t.message}"

        })

        disposables.add(disposable)
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


        isLoading.set(true)
        val disposable = covid19RxApiRepository.searchCountry(country)
            .subscribe({ it ->

            isLoading.set(false)

            val responseCountries = ArrayList<Country>()
            responseCountries.add(it)
            countriesLiveData.value = responseCountries

        }, { t ->
            isLoading.set(false)
            countriesErrorLiveData.value = "${t.message}"



        })
        disposables.add(disposable)
    }

    override fun destroy() {
        disposables.clear()
    }

}