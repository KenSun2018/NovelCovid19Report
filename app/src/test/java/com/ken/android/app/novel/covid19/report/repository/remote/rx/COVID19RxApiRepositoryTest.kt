package com.ken.android.app.novel.covid19.report.repository.remote.rx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import fake.Utils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.buffer
import okio.source
import org.junit.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets


class COVID19RxApiRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var covid19RxApiRepository: COVID19RxApiRepository

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        val covid19Service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(COVID19RxApiService::class.java)
        covid19RxApiRepository = COVID19RxApiRepository(covid19Service)
    }

    @After
    fun dropdown() {
        mockWebServer.shutdown()
    }


    @Test
    fun test_getGlobalCast_responseSuccess() {
        enqueueResponse("globalcase.json")

        val expectApiResponseString = Utils.readResFile("globalcase.json")
        val globalTotalCase = covid19RxApiRepository.getGlobalTotalCase().blockingGet()

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
        enqueueResponse("globalcase_empty.json")

        val expectApiResponseString = Utils.readResFile("globalcase_empty.json")

        val globalTotalCase = covid19RxApiRepository.getGlobalTotalCase().blockingGet()

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
        enqueueResponse("countries.json")
        val expectApiResponseString = Utils.readResFile("countries.json")

        val result = covid19RxApiRepository.getCountries().blockingGet()

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
        enqueueResponse("countries_empty.json")
        val expectApiResponseString = Utils.readResFile("countries_empty.json")


        val result = covid19RxApiRepository.getCountries().blockingGet()

        Assert.assertTrue(result.isEmpty())
        Assert.assertEquals(ArrayList::class.java, result::class.java)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source: BufferedSource = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}