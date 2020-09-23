package com.ken.android.app.novel.covid19.report.di.recyclerview.viewholder

import android.view.ViewGroup

abstract class DaggerViewHolderFactory {

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DaggerBaseViewHolder
}