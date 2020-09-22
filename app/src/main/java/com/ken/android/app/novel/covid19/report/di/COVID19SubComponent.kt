package com.ken.android.app.novel.covid19.report.di

import com.ken.android.app.novel.covid19.report.ui.info.FragmentCOVID19Info
import dagger.Subcomponent

/**
 * 練習 subcomponent 硬加的，已現行程式架構，其實用 AppComponent 就夠了
 * 因為每個 fragment 都是獨立 scope ，也沒有要有限跨域的需求
 * **/
@Subcomponent
interface COVID19SubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): COVID19SubComponent
    }

    fun inject(fragment : FragmentCOVID19Info)
}

