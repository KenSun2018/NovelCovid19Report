package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import io.reactivex.Single
import javax.inject.Inject

class TaiwanMaskRxApiRepository @Inject constructor(var apiService : TaiwanMaskRxApiService) : TaiwanMaskRxApiService {

    override fun getPoint(): Single<KiangGeoJson> {
        return apiService.getPoint()
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}