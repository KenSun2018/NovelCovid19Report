package com.ken.android.app.novel.covid19.report.ui.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.addTo

import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.BaseRxViewModel

class FragmentTWMaskViewModelRxImpl : BaseRxViewModel(), FragmentTWMaskViewModel {
    companion object{
        private const val TAG = "TWMaskViewModel"
    }
    private var mapRepository = TaiwanMaskRxApiRepository(OKHttpBaseInterceptor())
    private var geoJsonLiveData = MutableLiveData<KiangGeoJson>()



    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun getGeoJsonLiveData(): LiveData<KiangGeoJson> {
        return geoJsonLiveData
    }


    override fun loadMaskMapData() {
        isLoading.value = true

        mapRepository.getPoint()
            .doFinally{
                isLoading.value = false
            }
            .subscribe({

            geoJsonLiveData.value = it

        },{


        }).addTo(compositeDisposable)
    }


}