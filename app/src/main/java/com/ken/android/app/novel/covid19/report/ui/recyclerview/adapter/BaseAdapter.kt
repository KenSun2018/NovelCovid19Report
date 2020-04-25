package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    protected var baseAdapterItemDataList : ArrayList<BaseAdapterItemData> = ArrayList<BaseAdapterItemData>()

    private var onItemViewClickListener : View.OnClickListener ?= null


    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseAdapterViewHolderFactory.createViewHolder(parent, viewType)
    }

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val baseItemData = baseAdapterItemDataList[position]
        holder.setDataFromOnBindViewHolder(baseItemData.getRealData())
        holder.setOnViewClickListener(View.OnClickListener { v -> onItemViewClickListener?.onClick(v) })
    }

    final override fun getItemCount(): Int {
        return baseAdapterItemDataList.size
    }

    final fun setOnItemViewClickListener(onClickListener : View.OnClickListener){
        this.onItemViewClickListener = onClickListener
    }

    final override fun getItemViewType(position: Int): Int {
        return baseAdapterItemDataList[position].getViewType()
    }

    companion object{
        private const val TAG = "BaseAdapter"
    }
}