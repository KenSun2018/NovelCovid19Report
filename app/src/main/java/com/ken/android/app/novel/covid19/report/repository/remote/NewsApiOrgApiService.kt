package com.ken.android.app.novel.covid19.report.repository.remote

import com.ken.android.app.novel.covid19.report.repository.bean.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


// 資料來源
// https://newsapi.org
// https://newsapi.org/v2/top-headlines?q=covid&country=tw&apiKey={{apiKey}}

/**
 * 保留參考用，無使用，改用 Rx
 *
 * 資料來源 :
 * https://newsapi.org
 *
 * example :
 * https://newsapi.org/v2/top-headlines?q=covid&country=tw&apiKey={{apiKey}}
 * **/
interface NewsApiOrgApiService {

    @GET("v2/top-headlines")
    fun getTopHeadline(@Query("q") q : String,
                        @Query("country") country : String,
                        @Query("apiKey") apiKey : String) : Call<News>
}