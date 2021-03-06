package com.ken.android.app.novel.covid19.report.ui.info.daggerviewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetGlobalTotalCastBinding
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerBaseViewHolder
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerViewHolderFactory
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase

open class DaggerGlobalCaseViewHolder (val binding: WidgetGlobalTotalCastBinding) : DaggerBaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is GlobalTotalCase){
            binding.globalTotalCase = itemData
        }
    }

    open class Factory : DaggerViewHolderFactory(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaggerBaseViewHolder {
            val inflater: LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val widgetGlobalTotalCastBinding = DataBindingUtil.inflate<WidgetGlobalTotalCastBinding>(inflater, R.layout.widget_global_total_cast, parent, false)
            return DaggerGlobalCaseViewHolder(
                widgetGlobalTotalCastBinding
            )
        }
    }
}