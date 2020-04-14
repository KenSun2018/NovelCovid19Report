package com.ken.android.app.novel.covid19.report.repository.remote.rx


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import fake.Utils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Type

/**
 * 此測試可有可無，僅用來模擬 server team api 尚未實作，
 * 但已協調好 json 格式時，可以先放在自定義 folder ，
 * client 先接 retrofit 不用等 server
 * **/
internal class COVID19RxApiRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    private fun getFakeRepositoryFromFileName(fileName : String) : COVID19RxApiRepository{
        return COVID19RxApiRepository(Utils.getFakeResponseInterceptFromFile(fileName))
    }

    @Test
    fun test_getGlobalCast_responseSuccess() {
        val repository = getFakeRepositoryFromFileName("globalcase.json")
        val expectApiResponseString = Utils.readResFile("globalcase.json")

        val globalTotalCase = repository.getGlobalTotalCase().blockingGet()

        Assert.assertEquals(GlobalTotalCase::class.java, globalTotalCase::class.java)

        val gson = Gson()
        val expectResponse = gson.fromJson(expectApiResponseString, GlobalTotalCase::class.java)
        Assert.assertEquals(expectResponse.active, globalTotalCase.active)
        Assert.assertEquals(expectResponse.cases, globalTotalCase.cases)
        Assert.assertEquals(expectResponse.deaths, globalTotalCase.deaths)
        Assert.assertEquals(expectResponse.recovered, globalTotalCase.recovered)
        Assert.assertEquals(expectResponse.todayCases, globalTotalCase.todayCases)
        Assert.assertEquals(expectResponse.todayDeaths, globalTotalCase.todayDeaths)
        Assert.assertEquals(expectResponse.critical, globalTotalCase.critical)
        Assert.assertEquals(expectResponse.updated, globalTotalCase.updated)
    }



    @Test
    fun test_getGlobalCast_responseStatusCode200ButEmpty(){

        val repository = getFakeRepositoryFromFileName("globalcase_empty.json")
        val expectApiResponseString = Utils.readResFile("globalcase_empty.json")

        val globalTotalCase = repository.getGlobalTotalCase().blockingGet()

        val gson = Gson()
        val expectResponse = gson.fromJson(expectApiResponseString, GlobalTotalCase::class.java)


        Assert.assertEquals(expectResponse.active, globalTotalCase.active)
        Assert.assertEquals(expectResponse.cases, globalTotalCase.cases)
        Assert.assertEquals(expectResponse.deaths, globalTotalCase.deaths)
        Assert.assertEquals(expectResponse.recovered, globalTotalCase.recovered)
        Assert.assertEquals(expectResponse.todayCases, globalTotalCase.todayCases)
        Assert.assertEquals(expectResponse.todayDeaths, globalTotalCase.todayDeaths)
        Assert.assertEquals(expectResponse.critical, globalTotalCase.critical)
        Assert.assertEquals(expectResponse.updated, globalTotalCase.updated)
    }


    @Test
    fun test_getCountries_responseSuccess(){

        val repository = getFakeRepositoryFromFileName("countries.json")
        val expectApiResponseString = Utils.readResFile("countries.json")

        val result = repository.getCountries().blockingGet()

        Assert.assertTrue(result.isNotEmpty())

        Assert.assertEquals(ArrayList::class.java, result::class.java)

        val gson = Gson()
        val listType: Type = object : TypeToken<List<Country?>?>() {}.type
        val fakeResponseResult = gson.fromJson<List<Country>>(expectApiResponseString, listType)


        for(i in result.indices){

            val country = result[i]
            val expectResponse = fakeResponseResult[i]
            Assert.assertEquals(Country::class.java, country::class.java)

            Assert.assertEquals(expectResponse.active, country.active)
            Assert.assertEquals(expectResponse.cases, country.cases)
            Assert.assertEquals(expectResponse.deaths, country.deaths)
            Assert.assertEquals(expectResponse.recovered, country.recovered)
            Assert.assertEquals(expectResponse.todayCases, country.todayCases)
            Assert.assertEquals(expectResponse.todayDeaths, country.todayDeaths)
            Assert.assertEquals(expectResponse.critical, country.critical)
            Assert.assertEquals(expectResponse.updated, country.updated)
        }


    }

    @Test
    fun test_getCountries_responseStatusCode200ButEmpty(){

        val repository = getFakeRepositoryFromFileName("countries_empty.json")

        val result = repository.getCountries().blockingGet()

        Assert.assertTrue(result.isEmpty())
        Assert.assertEquals(ArrayList::class.java, result::class.java)
    }
}