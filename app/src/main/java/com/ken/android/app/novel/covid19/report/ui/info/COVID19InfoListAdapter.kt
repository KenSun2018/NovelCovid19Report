package com.ken.android.app.novel.covid19.report.ui.info

import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapter
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapterItemDataFactory

class COVID19InfoListAdapter : BaseAdapter(){
    private var globalTotalCase : GlobalTotalCase? = null
    private var countryList :List<Country> = ArrayList<Country>()
    private var covid19ChartData : COVID19ChartData? = null


    fun setGlobalCase(globalTotalCase: GlobalTotalCase){
        this.globalTotalCase = globalTotalCase
        mergeList()
    }

    fun setCountryList(countryList : List<Country>){
        this.countryList = countryList
        mergeList()
    }

    fun setCOVID19ChartData(covid19ChartData: COVID19ChartData){
        this.covid19ChartData = covid19ChartData
        mergeList()
    }

    //sorry, this is bad idea
    private fun mergeList(){
        baseAdapterItemDataList.clear()
        val anyDataList = ArrayList<Any>()

        if(globalTotalCase != null){
            anyDataList.add(globalTotalCase!!)
        }


        if(covid19ChartData != null){
            anyDataList.add(covid19ChartData!!)
        }



        if(countryList.isNotEmpty()){
            anyDataList.addAll(countryList)
        }

        baseAdapterItemDataList = BaseAdapterItemDataFactory.getList(anyDataList)
    }
}