package com.ken.android.app.novel.covid19.report.ui.country

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryBinding
import com.ken.android.app.novel.covid19.report.databinding.WidgetGlobalTotalCastBinding
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapter
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.BaseViewHolder
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.CountryViewHolder
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.GlobalCaseViewHolder

class CountryListAdapter : BaseAdapter(){


    private var globalTotalCase : GlobalTotalCase? = null
    private var countryList :List<Country> = ArrayList<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return when(viewType){
            R.layout.widget_global_total_cast -> {
                val widgetGlobalTotalCastBinding = DataBindingUtil.inflate<WidgetGlobalTotalCastBinding>(inflater, R.layout.widget_global_total_cast, parent, false)
                GlobalCaseViewHolder(widgetGlobalTotalCastBinding)
            }
            R.layout.widget_country -> {
                val widgetCountryBinding = DataBindingUtil.inflate<WidgetCountryBinding>(inflater, R.layout.widget_country, parent, false)
                CountryViewHolder(widgetCountryBinding)
            }
            else -> {
                throw IllegalArgumentException("unknown view_type = $viewType")
            }
        }
    }

    fun setGlobalCase(globalTotalCase: GlobalTotalCase){
        this.globalTotalCase = globalTotalCase
        mergeList()
    }

    fun setCountryList(countryList : List<Country>){
        this.countryList = countryList
        mergeList()
    }

    //sorry, this is bad idea
    private fun mergeList(){
        this.dataList.clear()

        if(globalTotalCase != null){
            dataList.add(globalTotalCase!!)
        }

        if(countryList.isNotEmpty()){
            dataList.addAll(countryList)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun onViewHolderBinded(holder: BaseViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return when (val data = dataList[position]){
            is GlobalTotalCase -> {
                R.layout.widget_global_total_cast
            }

            is Country ->{
                R.layout.widget_country
            }
            else -> {
                throw IllegalArgumentException(" unkonwn view_type from object = $data")
            }
        }
    }

}