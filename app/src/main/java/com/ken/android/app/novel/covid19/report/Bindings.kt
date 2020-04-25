package com.ken.android.app.novel.covid19.report

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ken.android.app.novel.covid19.report.utils.Log

class Bindings {
    companion object{
        private const val TAG = "Bindings"
        @JvmStatic
        @BindingAdapter("bind_image")
        fun loadImage(imageView: ImageView, imageURL : String?){
            val context = imageView.context
            Log.i(TAG, " imageURL = $imageURL")
            Glide.with(context)
                .load(imageURL)
                .into(imageView)
        }
    }
}