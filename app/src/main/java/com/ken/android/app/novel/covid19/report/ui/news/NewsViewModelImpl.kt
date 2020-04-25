package com.ken.android.app.novel.covid19.report.ui.news

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.NewsApiOrgRepository
import java.util.ArrayList

class NewsViewModelImpl : ViewModel(), NewsViewModel {

    private var newsApiRepository = NewsApiOrgRepository()

    private val newsLiveDataLazy : LiveData<ArrayList<NewsArticle>> by lazy {
        newsApiRepository.newsLiveData
    }

    private val newsErrorLiveDataLazy : LiveData<String> by lazy{
        newsApiRepository.newsErrorLiveData
    }

    private val isNewsLoading : LiveData<Boolean> by lazy {
        newsApiRepository.isNewsLoading
    }

    override fun loadNews(searchKey : String){
        newsApiRepository.loadNews(searchKey)
    }

    override fun getNewsLiveData(): LiveData<ArrayList<NewsArticle>> {
        return newsLiveDataLazy
    }


    override fun getErrorLiveData(): LiveData<String> {
        return newsErrorLiveDataLazy
    }

    override fun isLoading(): LiveData<Boolean> {
        return isNewsLoading
    }
}