package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryBinding
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryChartBinding
import com.ken.android.app.novel.covid19.report.databinding.WidgetGlobalTotalCastBinding
import com.ken.android.app.novel.covid19.report.databinding.WidgetNewsArticleBinding
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.*

object BaseAdapterViewHolderFactory {
    fun createViewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder {
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
            R.layout.widget_country_chart -> {
                val widgetCountryChartBinding = DataBindingUtil.inflate<WidgetCountryChartBinding>(inflater, R.layout.widget_country_chart, parent, false)
                COVID19ChartViewHolder(widgetCountryChartBinding)
            }
            R.layout.widget_news_article -> {
                val binding = DataBindingUtil.inflate<WidgetNewsArticleBinding>(inflater, R.layout.widget_news_article, parent , false)
                return NewsArticleViewHolder(binding)
            }
            else -> {
                throw IllegalArgumentException("unknown view_type = $viewType")
            }
        }
    }
}