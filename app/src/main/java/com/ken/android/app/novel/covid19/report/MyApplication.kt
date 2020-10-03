package com.ken.android.app.novel.covid19.report

import androidx.annotation.VisibleForTesting
import androidx.multidex.MultiDexApplication
import com.ken.android.app.novel.covid19.report.di.AppComponent
import com.ken.android.app.novel.covid19.report.di.DaggerAppComponent
import com.ken.android.app.novel.covid19.report.di.RepositoryModule
import com.ken.android.app.novel.covid19.report.di.api.APIModule
import com.ken.android.app.novel.covid19.report.di.recyclerview.DaggerBaseAdapterModule


class MyApplication : MultiDexApplication(){


    lateinit var appComponent : AppComponent
//    val appComponent : AppComponent by lazy {
//        DaggerAppComponent.builder()
//            .aPIModule(APIModule())
//            .daggerBaseAdapterModule(DaggerBaseAdapterModule())
//            .repositoryModule(RepositoryModule()).build()
//    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .aPIModule(APIModule())
            .repositoryModule(RepositoryModule())
            .daggerBaseAdapterModule(DaggerBaseAdapterModule())
            .build()
    }

    @VisibleForTesting
    fun setTestAppComponent(appComponent: AppComponent){
        this.appComponent = appComponent
    }

    
}