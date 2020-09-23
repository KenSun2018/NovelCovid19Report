package com.ken.android.app.novel.covid19.report.di.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerBaseViewHolder
import com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder.DaggerViewHolderFactory
import com.ken.android.app.novel.covid19.report.utils.Log
import javax.inject.Inject
import kotlin.reflect.KClass

class DaggerBaseAdapter @Inject constructor(
    private val dataObjToViewTypeMap : HashMap<KClass<*>, Int>,
    private val viewTypeToViewHolderFactoryMap : HashMap<Int, DaggerViewHolderFactory>
) : RecyclerView.Adapter<DaggerBaseViewHolder>(){

    companion object{
        private const val TAG = "DaggerBaseAdapter"
    }
    var onItemViewClickListener : View.OnClickListener ?= null
    var dataList = ArrayList<Any>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaggerBaseViewHolder {
        return viewTypeToViewHolderFactoryMap[viewType]?.onCreateViewHolder(parent, viewType)?:throw IllegalArgumentException(" unknown view type")
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DaggerBaseViewHolder, position: Int) {
        val data = dataList[position]
        holder.setDataFromOnBindViewHolder(data)
        holder.setOnViewClickListener(View.OnClickListener { v -> onItemViewClickListener?.onClick(v) })
    }

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "dataObjToViewTypeMap = $dataObjToViewTypeMap")
        Log.d(TAG, "viewTypeToViewHolderFactoryMap = $viewTypeToViewHolderFactoryMap")
        return dataObjToViewTypeMap[dataList[position].javaClass.kotlin]?:throw Exception("unknown support data object ${dataList[position]}")
    }
}