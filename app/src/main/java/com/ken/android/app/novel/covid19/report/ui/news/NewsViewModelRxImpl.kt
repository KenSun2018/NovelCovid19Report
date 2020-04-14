package com.ken.android.app.novel.covid19.report.ui.news


import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class NewsViewModelRxImpl : ViewModel(), NewsViewModel {

    companion object{
        private const val TAG = "NewsViewModelRxImpl"
    }
    private var newsApiRepository = NewsApiOrgRxApiRepository(OKHttpBaseInterceptor())

    private val newsLiveData = MutableLiveData<ArrayList<NewsArticle>> ()

    private val newsErrorLiveData = MutableLiveData<String>()

    private var isNewsLoading = ObservableBoolean (false)

    private var disposables = CompositeDisposable()

    @VisibleForTesting
    fun setMockRepository(repository : NewsApiOrgRxApiRepository){
        this.newsApiRepository = repository
    }

    @VisibleForTesting
    fun setMockLoading(observableBoolean: ObservableBoolean){
        isNewsLoading = observableBoolean
    }


    override fun loadNews(searchKey : String){
        isNewsLoading.set(true)

        val language = Locale.getDefault().country;

        val disposable = newsApiRepository.getTopHeadline(searchKey, language, BuildConfig.NEWS_API_KEY)
            .subscribe({ news ->
            newsLiveData.value = news.articles
            isNewsLoading.set(false)
        }, { t ->
            newsErrorLiveData.value = "${t.message}"
            isNewsLoading.set(false)
        })
        disposables.add(disposable)
    }

    override fun destroy() {
        disposables.clear()
    }

    override fun getNewsLiveData(): LiveData<ArrayList<NewsArticle>> {
        return newsLiveData
    }

    override fun getErrorLiveData(): LiveData<String> {
        return newsErrorLiveData
    }

    override fun isLoading(): ObservableBoolean {
        return isNewsLoading
    }

}