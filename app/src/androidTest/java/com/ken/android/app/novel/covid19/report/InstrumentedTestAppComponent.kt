package com.ken.android.app.novel.covid19.report

import com.ken.android.app.novel.covid19.report.di.AppComponent
import com.ken.android.app.novel.covid19.report.di.RepositoryModule
import com.ken.android.app.novel.covid19.report.di.api.APIModule
import com.ken.android.app.novel.covid19.report.di.recyclerview.DaggerBaseAdapterModule
import com.ken.android.app.novel.covid19.report.di.viewmodel.DaggerViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class, DaggerViewModelModule::class, DaggerBaseAdapterModule::class, FakeRepositoryModule::class])
interface InstrumentedTestAppComponent : AppComponent {
}