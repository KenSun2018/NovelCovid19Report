package com.ken.android.app.novel.covid19.report.ui.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.extension.addTo
import com.ken.android.app.novel.covid19.report.plusAssign
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.BaseRxViewModel

class FragmentTWMaskViewModelRxImpl(
    private val mapRepository: TaiwanMaskRxApiRepository
) : BaseRxViewModel(), FragmentTWMaskViewModel {
    companion object{
        private const val TAG = "TWMaskViewModel"
    }
    private val geoJsonLiveData = MutableLiveData<KiangGeoJson>()



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


        })
            .addTo(compositeDisposable)
    }

    class Factory(
        private val mapRepository: TaiwanMaskRxApiRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FragmentTWMaskViewModelRxImpl(
                mapRepository
            ) as T
        }
    }
}