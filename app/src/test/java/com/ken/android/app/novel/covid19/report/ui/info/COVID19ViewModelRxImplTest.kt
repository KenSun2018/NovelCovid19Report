package com.ken.android.app.novel.covid19.report.ui.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
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


    private lateinit var covid19ViewModel : COVID19InfoViewModel

    @MockK
    private lateinit var mockRepository: COVID19RxApiRepository

    @MockK
    private lateinit var mockLoading : MutableLiveData<Boolean>

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        //dummy
        covid19ViewModel = COVID19InfoViewModel.RxFactory(mockRepository).create(COVID19InfoViewModelRxImpl::class.java)
        covid19ViewModel.setMockLoading(mockLoading)

        // if mockLoading is not false that mean developer is not set isLoading.value = false
        every { mockLoading.value } returns false
    }


    @Test
    fun test_viewModelLoadGlobalTotalCaseApi(){
        val expectGlobalTotalCase = GlobalTotalCase()
        expectGlobalTotalCase.todayDeaths = "0";
        expectGlobalTotalCase.deaths = "0"
        expectGlobalTotalCase.todayCases = "1"
        expectGlobalTotalCase.cases = "2"

        //stub
        every { mockRepository.getGlobalTotalCase() } returns Single.just(expectGlobalTotalCase)
        covid19ViewModel.loadGlobalTotalCase()

        verifySequence {
            mockLoading.value = true // verify show progress bar
            mockRepository.getGlobalTotalCase() //verify call api
            mockLoading.value = false // verify hide progress bar
        }

        Assert.assertEquals(expectGlobalTotalCase, covid19ViewModel.getGlobalCaseLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().value)
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
            mockLoading.value = true
            mockRepository.getGlobalTotalCase()
            mockLoading.value = false

        }

        Assert.assertEquals(exceptionMsg, covid19ViewModel.getGlobalCaseErrorLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().value)
    }


    @Test
    fun test_viewModelLoadCountriesApi(){

        val expectCountriesList = ArrayList<Country>()

        val countryCount = 20;
        for(i in 1 until countryCount){
            val country = Country()
            country.country = "country $i"
            country.deaths = "$i"
            country.recovered = "9999999$i"
            country.cases = "${i*countryCount}"
            country.todayCases = "$i"
            expectCountriesList.add(country)
        }
        val expectCOVID19ChartData =
            COVID19ChartData(
                expectCountriesList
            );


        every { mockRepository.getCountries("deaths") } returns Single.just(expectCountriesList)

        covid19ViewModel.loadCountries()

        verifySequence {
            mockLoading.value = true
            mockRepository.getCountries("deaths")
            mockLoading.value = false
        }

        Assert.assertEquals(ArrayList::class.java, covid19ViewModel.getCountriesLiveData().value?.javaClass)
        Assert.assertEquals(expectCountriesList, covid19ViewModel.getCountriesLiveData().value)
        Assert.assertEquals(false, covid19ViewModel.isLoading().value)
        Assert.assertEquals(expectCountriesList.size, covid19ViewModel.getCountriesLiveData().value?.size)

    }





}