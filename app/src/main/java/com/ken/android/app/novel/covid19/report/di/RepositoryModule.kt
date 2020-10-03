package com.ken.android.app.novel.covid19.report.di

import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepositoryImpl
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiService
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    open fun provideCOVID19RxApiRepository(coviD19RxApiService: COVID19RxApiService) : COVID19RxApiRepository{
        return COVID19RxApiRepositoryImpl(coviD19RxApiService)
    }
}
