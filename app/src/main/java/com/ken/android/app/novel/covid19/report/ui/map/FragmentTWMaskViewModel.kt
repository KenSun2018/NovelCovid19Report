package com.ken.android.app.novel.covid19.report.ui.map

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson

interface FragmentTWMaskViewModel {

    fun isLoading() : LiveData<Boolean>
    fun getGeoJsonLiveData() : LiveData<KiangGeoJson>
    fun loadMaskMapData()
    fun destroy()
}