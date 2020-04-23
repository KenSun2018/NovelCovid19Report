package com.ken.android.app.novel.covid19.report.ui.info.data

import android.util.Log
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import java.lang.Exception
import kotlin.math.ceil

class COVID19ChartData : ValueFormatter {
    companion object{
        private const val TAG = "COVID19ChartData";
        const val DISPLAY_MAX_AMOUNT = 10
    }

    private var filterCountryMap = HashMap<Int, Country>()
    private var deathsEntryList = ArrayList<BarEntry>()
    private var casesEntryList = ArrayList<Entry>()


    constructor(countries : List<Country>){
        for (i in countries.indices){

            //前10大
            if(i >= DISPLAY_MAX_AMOUNT){
                break
            }


            val country = countries[i]
            var deaths = -1f
            try{
                deaths = country.deaths.toFloat()
                Log.i(TAG, " country = ${country.country} deaths = $deaths")
            }catch (e : Exception){
                Log.e(TAG, " parse deaths int exception ${e.message}")
            }
            var totalCases = -1f
            try{
                totalCases = country.cases.toFloat()
                Log.i(TAG, " country = ${country.country} totalCases = $totalCases")
            }catch (e : Exception){
                Log.e(TAG, " parse totalCases int exception ${e.message}")
            }

            if(deaths == -1f || totalCases == -1f){
                continue
            }


            if( country.countryInfo._id == -1 ){
                Log.e(TAG, " id = ${country.countryInfo._id}, ${country.country}")
                continue
            }
            Log.i(TAG, "id = ${i}, ${country.country}")
            filterCountryMap[i] = country
            val barEntry = BarEntry(i.toFloat(), deaths)
            deathsEntryList.add(barEntry)

            val caseBarEntry = Entry(i.toFloat(), totalCases)
            casesEntryList.add(caseBarEntry)
        }

        Log.e(TAG, " size = ${countries.size}, filedCountry.size = ${filterCountryMap.size}")
    }

    fun getDeathsBarEntries() : ArrayList<BarEntry>{
        return deathsEntryList
    }

    fun getCasesEntries() :  ArrayList<Entry>{
        return casesEntryList
    }

    override fun getFormattedValue(value: Float): String {
        var targetValue = 0;
        if(value < 0){
            targetValue = 0;
        }else{
            targetValue = ceil(value.toDouble()).toInt()
        }

        val country = filterCountryMap[targetValue]
        if(country == null){
            Log.e(TAG, " getFormattedValue ($targetValue), country is null")
            return ""
        }
        Log.e(TAG, " getFormattedValue ($targetValue), country = ${country.country}")
        return country.country
    }
}