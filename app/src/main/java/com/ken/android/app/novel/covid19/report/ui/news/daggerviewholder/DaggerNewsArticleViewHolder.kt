package com.ken.android.app.novel.covid19.report.ui.news.daggerviewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetNewsArticleBinding
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerBaseViewHolder
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerViewHolderFactory
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle

class DaggerNewsArticleViewHolder(var binding : WidgetNewsArticleBinding) : DaggerBaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is NewsArticle){
            binding.article = itemData
        }
    }

    open class Factory : DaggerViewHolderFactory(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaggerBaseViewHolder {
            val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val widgetNewsArticleBinding = DataBindingUtil.inflate<WidgetNewsArticleBinding>(inflater, R.layout.widget_news_article, parent, false)
            return DaggerNewsArticleViewHolder(widgetNewsArticleBinding)
        }

    }
}