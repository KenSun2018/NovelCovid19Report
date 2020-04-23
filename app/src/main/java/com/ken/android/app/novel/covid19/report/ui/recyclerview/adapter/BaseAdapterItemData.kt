package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter


interface BaseAdapterItemData {
    fun getViewType() : Int
    fun getRealData() : Any
}