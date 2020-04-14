package com.ken.android.app.novel.covid19.report

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Bindings {
    companion object{
        @JvmStatic
        @BindingAdapter("bind_image")
        fun loadImage(imageView: ImageView, imageURL : String?){
            val context = imageView.context
            Glide.with(context)
                .load(imageURL)
                .into(imageView)
        }
    }
}