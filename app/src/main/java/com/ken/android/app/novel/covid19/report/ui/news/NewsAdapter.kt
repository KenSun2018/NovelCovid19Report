package com.ken.android.app.novel.covid19.report.ui.news

import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapter
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapterItemDataFactory


class NewsAdapter : BaseAdapter(){
    fun setNewsArticle(newsDataList : ArrayList<NewsArticle>){
        baseAdapterItemDataList.clear()
        baseAdapterItemDataList.addAll(BaseAdapterItemDataFactory.getList(newsDataList));
    }
}