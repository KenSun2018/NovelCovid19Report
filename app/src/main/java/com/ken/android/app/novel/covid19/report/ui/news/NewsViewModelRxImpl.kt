package com.ken.android.app.novel.covid19.report.ui.news


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.addTo
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.BaseRxViewModel
import java.util.*

class NewsViewModelRxImpl(private val newsApiRepository : NewsApiOrgRxApiRepository) : BaseRxViewModel(), NewsViewModel {

    companion object{
        private const val TAG = "NewsViewModelRxImpl"
    }


    private val newsLiveData = MutableLiveData<ArrayList<NewsArticle>> ()

    private val newsErrorLiveData = MutableLiveData<String>()


    override fun loadNews(searchKey : String){
        isLoading.value = true

        val language = Locale.getDefault().country;

        newsApiRepository.getTopHeadline(searchKey, language, BuildConfig.NEWS_API_KEY)
            .doFinally{
                isLoading.value = false
            }
            .subscribe({ news ->
            newsLiveData.value = news.articles

        }, { t ->
            newsErrorLiveData.value = "${t.message}"
        }).addTo(compositeDisposable)

    }


    override fun getNewsLiveData(): LiveData<ArrayList<NewsArticle>> {
        return newsLiveData
    }

    override fun getErrorLiveData(): LiveData<String> {
        return newsErrorLiveData
    }

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

}