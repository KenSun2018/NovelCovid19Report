package com.ken.android.app.novel.covid19.report.di

import android.content.Context
import com.ken.android.app.novel.covid19.report.di.viewmodel.DaggerViewModelInjectionModule
import com.ken.android.app.novel.covid19.report.ui.info.FragmentCOVID19Info
import com.ken.android.app.novel.covid19.report.ui.map.FragmentTWMaskMap
import com.ken.android.app.novel.covid19.report.ui.news.FragmentNews
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component (modules = [APIModule::class, DaggerViewModelInjectionModule::class])
interface AppComponent {


    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context) : AppComponent
    }

    fun inject(fragment : FragmentCOVID19Info)

    fun inject(fragmentNews : FragmentNews)

    fun inject (fragment : FragmentTWMaskMap)
}