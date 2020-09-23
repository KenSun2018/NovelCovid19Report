package com.ken.android.app.novel.covid19.report.di.recyclerview

import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.ui.info.daggerviewholder.DaggerGlobalCaseViewHolder
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerViewHolderFactory
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.ui.info.data.COVID19ChartData
import com.ken.android.app.novel.covid19.report.ui.info.daggerviewholder.DaggerCOVID19ChartViewHolder
import com.ken.android.app.novel.covid19.report.ui.info.daggerviewholder.DaggerCountryViewHolder
import com.ken.android.app.novel.covid19.report.ui.news.daggerviewholder.DaggerNewsArticleViewHolder
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
class DaggerBaseAdapterModule {


    @Provides
    fun provideDataToViewTypeMap() : HashMap<KClass<*>, Int>{
        return hashMapOf(
            GlobalTotalCase::class to R.layout.widget_global_total_cast,
            Country::class to R.layout.widget_country,
            COVID19ChartData::class to R.layout.widget_country_chart,
            NewsArticle::class to R.layout.widget_news_article
        )
    }

    @Provides
    fun provideViewTypeToViewHolderFactory() : HashMap<Int, DaggerViewHolderFactory>{
        return hashMapOf(
            R.layout.widget_global_total_cast to DaggerGlobalCaseViewHolder.Factory(),
            R.layout.widget_country to DaggerCountryViewHolder.Factory(),
            R.layout.widget_country_chart to DaggerCOVID19ChartViewHolder.Factory(),
            R.layout.widget_news_article to DaggerNewsArticleViewHolder.Factory()
        )
    }


}