package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter

import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle


object BaseAdapterItemDataFactory {

    fun getList(dataList : List<Any>) : ArrayList<BaseAdapterItemData>{
        val itemDataList = ArrayList<BaseAdapterItemData>();
        for(data in dataList){
            val itemData = get(data);
            itemDataList.add(itemData)
        }
        return itemDataList
    }


    fun get(data: Any) : BaseAdapterItemData{
        //不想讓資料物件去實作，要實作也行
        return object : BaseAdapterItemData{
            override fun getViewType(): Int {
                return when(data){
                    is Country -> {
                        R.layout.widget_country
                    }
                    is GlobalTotalCase -> {
                        R.layout.widget_global_total_cast
                    }

                    is COVID19ChartData -> {
                        R.layout.widget_country_chart
                    }
                    is NewsArticle -> {
                        R.layout.widget_news_article
                    }
                    else ->{
                        throw IllegalArgumentException(" unknown data")
                    }

                }
            }

            override fun getRealData(): Any {
                return data
            }
        }
    }


}