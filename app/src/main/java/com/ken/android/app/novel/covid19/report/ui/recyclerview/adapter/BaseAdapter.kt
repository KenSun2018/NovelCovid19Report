package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder.BaseViewHolder

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    protected var dataList : ArrayList<Any> = ArrayList<Any>()

    private var onItemViewClickListener : View.OnClickListener ?= null


    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val targetData = dataList[position]

        holder.setDataFromOnBindViewHolder(targetData)
        holder.setOnViewClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onItemViewClickListener?.onClick(v)
            }
        })

        onViewHolderBinded(holder, position)
    }

    abstract fun onViewHolderBinded(holder: BaseViewHolder, position: Int)

    final fun setOnItemViewClickListener(onClickListener : View.OnClickListener){
        this.onItemViewClickListener = onClickListener
    }

    companion object{
        private const val TAG = "BaseAdapter"
    }
}