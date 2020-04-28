package com.ken.android.app.novel.covid19.report.ui.map

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository

interface FragmentTWMaskViewModel {

    fun isLoading() : LiveData<Boolean>
    fun getGeoJsonLiveData() : LiveData<KiangGeoJson>
    fun loadMaskMapData()


    @VisibleForTesting
    fun setMockLoading(isLoading: MutableLiveData<Boolean>)

    open class RxFactory(private val mapRepository : TaiwanMaskRxApiRepository) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FragmentTWMaskViewModelRxImpl(mapRepository) as T
        }
    }
}