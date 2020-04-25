package com.ken.android.app.novel.covid19.report.ui.info.viewholder

import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryBinding
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.BaseViewHolder

class CountryViewHolder(var binding: WidgetCountryBinding) : BaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is Country){
            binding.country = itemData
        }
    }
}