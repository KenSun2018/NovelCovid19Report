package com.ken.android.app.novel.covid19.report.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.databinding.WidgetNewsArticleBinding
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseAdapter
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.BaseViewHolder
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.NewsArticleViewHolder


class NewsAdapter : BaseAdapter(){


    fun setNewsArticle(newsDataList : ArrayList<NewsArticle>){
        dataList.clear()
        dataList.addAll(newsDataList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = DataBindingUtil.inflate<WidgetNewsArticleBinding>(inflater, R.layout.widget_news_article, parent , false)
        return NewsArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onViewHolderBinded(holder: BaseViewHolder, position: Int) {

    }


}