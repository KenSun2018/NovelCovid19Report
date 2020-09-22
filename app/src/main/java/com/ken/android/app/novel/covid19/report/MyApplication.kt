package com.ken.android.app.novel.covid19.report

import androidx.multidex.MultiDexApplication
import com.ken.android.app.novel.covid19.report.di.AppComponent
import com.ken.android.app.novel.covid19.report.di.DaggerAppComponent


class MyApplication : MultiDexApplication(){


    val appComponent : AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}