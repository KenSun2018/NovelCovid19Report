package com.ken.android.app.novel.covid19.report.repository.remote


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.repository.bean.News
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


/**
 * 保留參考用，無使用，改用 Rx
 *
 * 資料來源 :
 * https://newsapi.org
 *
 * example :
 * https://newsapi.org/v2/top-headlines?q=covid&country=tw&apiKey={{apiKey}}
 * **/
class NewsApiOrgRepository {

    companion object{
        private const val TAG = "NewsApiOrgRepository"
        private const val BASE_URL = "https://newsapi.org/"
    }

    private var apiService : NewsApiOrgApiService

    var newsLiveData = MutableLiveData<ArrayList<NewsArticle>>()
    var newsErrorLiveData = MutableLiveData<String>()
    val isNewsLoading = ObservableBoolean(false)

    init {
        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(NewsApiOrgApiService::class.java)
    }

    fun loadNews(searchKey : String){
        isNewsLoading.set(true)
        val language = Locale.getDefault().country;

        apiService.getTopHeadline(searchKey, language, BuildConfig.NEWS_API_KEY).enqueue(object : Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                isNewsLoading.set(false)

                newsErrorLiveData.value = "${t.message}"
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                isNewsLoading.set(false)
                if (!response.isSuccessful){
                    newsErrorLiveData.value = "loadNews response but not successful"
                    return
                }

                val news = response.body()
                if(news == null ){
                    newsErrorLiveData.value = "loadNews response success but news is null"
                    return
                }

                val articles = news.articles
                if(articles.isEmpty()){
                    newsErrorLiveData.value = "loadNews response success but articles is empty, news.status = ${news.status}, totalResults = ${news.totalResults}"
                    return
                }


                newsLiveData.value = articles

            }

        })
    }
}