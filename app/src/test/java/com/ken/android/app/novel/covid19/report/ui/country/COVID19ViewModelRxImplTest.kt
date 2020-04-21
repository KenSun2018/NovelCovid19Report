package com.ken.android.app.novel.covid19.report.ui.country

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableBoolean
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException


//test flow
class COVID19ViewModelRxImplTest{


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private var covid19ViewModel = COVID19InfoViewModelRxImpl()

    @MockK
    private lateinit var mockRepository: COVID19RxApiRepository

    @MockK
    private lateinit var mockLoading : ObservableBoolean

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        covid19ViewModel.setMockRepository(mockRepository)
        covid19ViewModel.setMockLoading(mockLoading)

        // if mockLoading is not false that mean developer is not set isLoading.set(false)
        every { mockLoading.get() } returns false
    }


    @Test
    fun test_viewModelLoadGlobalTotalCaseApi(){
        val expectGlobalTotalCase = GlobalTotalCase()
        expectGlobalTotalCase.todayDeaths = "0";
        expectGlobalTotalCase.deaths = "0"
        expectGlobalTotalCase.todayCases = "1"
        expectGlobalTotalCase.cases = "2"

        every { mockRepository.getGlobalTotalCase() } returns Single.just(expectGlobalTotalCase)
        covid19ViewModel.loadGlobalTotalCase()

        verifySequence {
            mockLoading.set(true) // verify show progress bar
            mockRepository.getGlobalTotalCase() //verify call api
            mockLoading.set(false) // verify hide progress bar
        }

        Assert.assertEquals(expectGlobalTotalCase, covid19ViewModel.getGlobalCaseLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().get())
        Assert.assertEquals(expectGlobalTotalCase.todayDeaths, covid19ViewModel.getGlobalCaseLiveData().value?.todayDeaths)
        Assert.assertEquals(expectGlobalTotalCase.deaths, covid19ViewModel.getGlobalCaseLiveData().value?.deaths)
        Assert.assertEquals(expectGlobalTotalCase.todayCases, covid19ViewModel.getGlobalCaseLiveData().value?.todayCases)
        Assert.assertEquals(expectGlobalTotalCase.cases, covid19ViewModel.getGlobalCaseLiveData().value?.cases)
    }

    @Test
    fun test_viewModelLoadGlobalTotalCaseApiException(){
        val exceptionMsg = "test io exception"
        val ex = IOException(exceptionMsg);
        every { mockRepository.getGlobalTotalCase() } returns Single.error(ex)
        covid19ViewModel.loadGlobalTotalCase()

        verifySequence {
            mockLoading.set(true)
            mockRepository.getGlobalTotalCase()
            mockLoading.set(false)
        }

        Assert.assertEquals(exceptionMsg, covid19ViewModel.getGlobalCaseErrorLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().get())
    }


    @Test
    fun test_viewModelLoadCountriesApi(){

        val countriesList = ArrayList<Country>()

        for(i in 1 until 10){
            val country = Country()
            country.country = "country $i"
            country.deaths = "$i"
            country.recovered = "9999999$i"
            country.cases = "${i*10}"
            country.todayCases = "$i"
            countriesList.add(country)
        }

        every { mockRepository.getCountries("deaths") } returns Single.just(countriesList)
        covid19ViewModel.loadCountries()

        verifySequence {
            mockLoading.set(true)
            mockRepository.getCountries("deaths")
            mockLoading.set(false)
        }

        Assert.assertEquals(ArrayList::class.java, covid19ViewModel.getCountriesLiveData().value?.javaClass)
        Assert.assertEquals(countriesList, covid19ViewModel.getCountriesLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().get())
        Assert.assertEquals(countriesList.size, covid19ViewModel.getCountriesLiveData().value?.size)
    }





}