package com.ken.android.app.novel.covid19.report.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.NewsApiOrgRepository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import java.util.*

interface NewsViewModel {
    fun getNewsLiveData() : LiveData<ArrayList<NewsArticle>>
    fun getErrorLiveData() : LiveData<String>
    fun isLoading() : LiveData<Boolean>
    fun loadNews(searchKey : String)


    open class RxFactory(private val newsApiOrgRxApiRepository : NewsApiOrgRxApiRepository) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModelRxImpl(newsApiOrgRxApiRepository) as T
        }
    }

    open class Factory(private val newsApiOrgRepository : NewsApiOrgRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModelImpl(newsApiOrgRepository) as T
        }
    }
}