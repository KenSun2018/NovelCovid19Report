package com.ken.android.app.novel.covid19.report.ui.recyclerview.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView : View)   : RecyclerView.ViewHolder(itemView){

    private var onClickListenerCallbackToParent : View.OnClickListener? = null

    final fun setDataFromOnBindViewHolder(itemData: Any){
        setData(itemData)
        itemView.tag = itemData
        itemView.isClickable = true
        itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                onClickListenerCallbackToParent?.onClick(v)
            }

        })
    }
    fun setOnViewClickListener(onClickListener: View.OnClickListener){
        this.onClickListenerCallbackToParent = onClickListener
    }
    protected abstract fun setData(itemData : Any)

    companion object{
        private const val TAG = "BaseViewHolder"
    }
}