package com.ken.android.app.novel.covid19.report.ui.info.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import fake.Utils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Type

class COVID19ChartDataTest{


    private lateinit var covid19Countries : List<Country>
    private lateinit var covid19ChartData : COVID19ChartData
    @Before
    fun setUp(){
        val expectApiResponseString = Utils.readResFile("covid19_all_countries.json")
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Country?>?>() {}.type
        covid19Countries = gson.fromJson<List<Country>>(expectApiResponseString, listType)
        covid19ChartData = COVID19ChartData(covid19Countries)
    }


    @Test
    fun test_covid19ChartData_DISPLAY_MAX_AMOUNT(){


        Assert.assertEquals(true, covid19ChartData.getCasesEntries().size == COVID19ChartData.DISPLAY_MAX_AMOUNT)
        Assert.assertEquals(true, covid19ChartData.getDeathsBarEntries().size == COVID19ChartData.DISPLAY_MAX_AMOUNT)
        Assert.assertEquals(true, covid19ChartData.getDeathsBarEntries().size == covid19ChartData.getCasesEntries().size)


    }

    @Test
    fun test_covid19ChartData_Top10CasesToChartY(){
        val caseEntries = covid19ChartData.getCasesEntries()
        val deathsBarEntries = covid19ChartData.getDeathsBarEntries()
        for( i in caseEntries.indices){
            val entry = caseEntries[i]
            val country = covid19Countries[i]
            try{
                val cases = country.cases.toFloat()
                Assert.assertEquals(entry.y,  cases)
            }catch (e : Exception){
                System.err.println(" cast exception")
            }
        }
    }


    @Test
    fun test_covid19ChartData_Top10DeathToBarEntityY(){

        val deathsBarEntries = covid19ChartData.getDeathsBarEntries()
        for( i in deathsBarEntries.indices){
            val entry = deathsBarEntries[i]
            val country = covid19Countries[i]
            try{
                val deathsFloat = country.deaths.toFloat()
                Assert.assertEquals(entry.y,  deathsFloat)
            }catch (e : Exception){
                System.err.println(" cast exception")
            }
        }
    }




}