package com.ken.android.app.novel.covid19.report

import com.ken.android.app.novel.covid19.report.di.RepositoryModule
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiService
import com.ken.android.app.novel.covid19.report.ui.info.FakeCOVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.utils.Log
import dagger.Module
import dagger.Provides

@Module
class FakeRepositoryModule {
    companion object{
        private const val TAG = "FakeRepositoryModule"
    }

    @Provides
    fun provideCOVID19RxApiRepository(coviD19RxApiService: COVID19RxApiService): COVID19RxApiRepository {
        Log.w(TAG, " provideCOVID19RxApiRepository")
        return FakeCOVID19RxApiRepository()
    }
}