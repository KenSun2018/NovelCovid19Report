package com.ken.android.app.novel.covid19.report.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.ui.info.COVID19InfoViewModelRxImpl
import com.ken.android.app.novel.covid19.report.ui.map.FragmentTWMaskViewModelRxImpl
import com.ken.android.app.novel.covid19.report.ui.news.NewsViewModelRxImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DaggerViewModelModule{
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(COVID19InfoViewModelRxImpl::class)
    abstract fun bindCovid19ViewModel(covid19InfoViewModelRxImpl: COVID19InfoViewModelRxImpl) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModelRxImpl::class)
    abstract fun bindNewsViewModel(viewModel: NewsViewModelRxImpl) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentTWMaskViewModelRxImpl::class)
    abstract fun bindTWMaskViewModel(viewModel: FragmentTWMaskViewModelRxImpl): ViewModel

}