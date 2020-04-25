package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import io.reactivex.Single
import retrofit2.http.GET

/**
 * 資料提供者 https://github.com/kiang
 * **/
//https://raw.githubusercontent.com/kiang/pharmacies/master/json/points.json
interface TaiwanMaskRxApiService {
    @GET("kiang/pharmacies/master/json/points.json")
    fun getPoint() : Single<KiangGeoJson>
}