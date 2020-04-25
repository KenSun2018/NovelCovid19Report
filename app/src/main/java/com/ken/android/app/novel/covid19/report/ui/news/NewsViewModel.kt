package com.ken.android.app.novel.covid19.report.ui.news

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import java.util.ArrayList

interface NewsViewModel {
    fun getNewsLiveData() : LiveData<ArrayList<NewsArticle>>
    fun getErrorLiveData() : LiveData<String>
    fun isLoading() : LiveData<Boolean>
    fun loadNews(searchKey : String)


}