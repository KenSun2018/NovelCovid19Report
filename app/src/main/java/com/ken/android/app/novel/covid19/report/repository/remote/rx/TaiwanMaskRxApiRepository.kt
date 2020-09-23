package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.di.api.TWMaskService
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class TaiwanMaskRxApiRepository @Inject constructor(@TWMaskService retrofit: Retrofit) : TaiwanMaskRxApiService {
//    companion object{
//        private const val TAG = "TaiwanMaskRxApiRepository"
//        private const val BASE_URL = "https://raw.githubusercontent.com/"
//    }

    private var apiService : TaiwanMaskRxApiService = retrofit.create(TaiwanMaskRxApiService::class.java)

    override fun getPoint(): Single<KiangGeoJson> {
        return apiService.getPoint()
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}