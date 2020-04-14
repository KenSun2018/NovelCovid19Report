package com.ken.android.app.novel.covid19.report.ui.map


import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository
import io.reactivex.disposables.CompositeDisposable

class FragmentTWMaskViewModelRxImpl : FragmentTWMaskViewModel {
    companion object{
        private const val TAG = "TWMaskViewModel"
    }
    private var mapRepository = TaiwanMaskRxApiRepository(OKHttpBaseInterceptor())
    private var geoJsonLiveData = MutableLiveData<KiangGeoJson>()

    private var loading = ObservableBoolean(false)
    private var disposables = CompositeDisposable()

    override fun isLoading(): ObservableBoolean {
        return loading
    }

    override fun getGeoJsonLiveData(): LiveData<KiangGeoJson> {
        return geoJsonLiveData
    }

    @VisibleForTesting
    fun setMockLoading(observableBoolean: ObservableBoolean){
        loading = observableBoolean
    }

    override fun loadMaskMapData() {
        loading.set(true)

        val disposable = mapRepository.getPoint().subscribe({
            loading.set(false)
            geoJsonLiveData.value = it

        },{
            loading.set(false)

        })
        disposables.add(disposable)
    }

    override fun destroy() {
        disposables.clear()
    }
}