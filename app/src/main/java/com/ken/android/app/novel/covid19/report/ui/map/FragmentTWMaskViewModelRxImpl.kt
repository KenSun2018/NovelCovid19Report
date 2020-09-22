package com.ken.android.app.novel.covid19.report.ui.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.addTo
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.BaseRxViewModel
import javax.inject.Inject

class FragmentTWMaskViewModelRxImpl @Inject constructor(private val mapRepository : TaiwanMaskRxApiRepository) : BaseRxViewModel(), FragmentTWMaskViewModel {
    companion object{
        private const val TAG = "TWMaskViewModel"
    }

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