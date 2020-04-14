package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder

import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.databinding.WidgetNewsArticleBinding

class NewsArticleViewHolder (var binding : WidgetNewsArticleBinding): BaseViewHolder(binding.root){
    override fun setData(itemData: Any) {
        if(itemData is NewsArticle){
            binding.article = itemData
        }
    }
}