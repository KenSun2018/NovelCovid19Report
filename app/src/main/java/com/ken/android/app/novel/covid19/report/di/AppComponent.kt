package com.ken.android.app.novel.covid19.report.di

import android.content.Context
import com.ken.android.app.novel.covid19.report.di.viewmodel.DaggerViewModelModule
import com.ken.android.app.novel.covid19.report.ui.info.FragmentCOVID19Info
import com.ken.android.app.novel.covid19.report.ui.map.FragmentTWMaskMap
import com.ken.android.app.novel.covid19.report.ui.news.FragmentNews
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component (modules = [APIModule::class, DaggerViewModelModule::class])
interface AppComponent {


    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context) : AppComponent
    }

    fun getCOVID19SubComponent() : COVID19SubComponent.Factory


    fun inject(fragmentNews : FragmentNews)

    fun inject (fragment : FragmentTWMaskMap)
}