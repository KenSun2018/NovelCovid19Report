package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder

import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.databinding.WidgetCountryBinding

class CountryViewHolder(var binding: WidgetCountryBinding) : BaseViewHolder(binding.root) {
    override fun setData(itemData: Any) {
        if(itemData is Country){
            binding.country = itemData
        }
    }
}