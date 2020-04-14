package com.ken.android.app.novel.covid19.report.repository.remote.rx

import com.ken.android.app.novel.covid19.report.RxCustomSchedulers
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import io.reactivex.Single
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TaiwanMaskRxApiRepository(interceptor: Interceptor) : BaseApiConfig(interceptor), TaiwanMaskRxApiService {
    companion object{
        private const val TAG = "TaiwanMaskRxApiRepository"
        private const val BASE_URL = "https://raw.githubusercontent.com/"
    }

    private var apiService : TaiwanMaskRxApiService

    init {

        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(TaiwanMaskRxApiService::class.java)
    }

    override fun getPoint(): Single<KiangGeoJson> {
        return apiService.getPoint()
            .subscribeOn(RxCustomSchedulers.io())
            .observeOn(RxCustomSchedulers.mainThread())
    }
}