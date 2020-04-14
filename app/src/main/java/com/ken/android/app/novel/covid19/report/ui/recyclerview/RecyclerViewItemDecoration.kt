package com.ken.android.app.novel.covid19.report.ui.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ken.android.app.novel.covid19.report.R

class RecyclerViewItemDecoration  : RecyclerView.ItemDecoration(){


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {


        val interval = view.resources.getDimensionPixelSize(R.dimen.recyclerview_interval)
        outRect.left = interval
        outRect.right = interval
        outRect.bottom = interval
    }
}