package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter

@Deprecated("replace to [DaggerBaseAdapter]")
interface BaseAdapterItemData {
    fun getViewType() : Int
    fun getRealData() : Any
}