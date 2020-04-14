package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder

import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.databinding.WidgetGlobalTotalCastBinding

class GlobalCaseViewHolder(var binding : WidgetGlobalTotalCastBinding) : BaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is GlobalTotalCase){
            binding.globalTotalCase = itemData
        }
    }

}