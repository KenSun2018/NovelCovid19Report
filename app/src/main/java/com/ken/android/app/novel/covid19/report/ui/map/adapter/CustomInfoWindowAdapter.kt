package com.ken.android.app.novel.covid19.report.ui.map.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetMapCustomInfoWindowBinding
import com.ken.android.app.novel.covid19.report.repository.bean.Feature
import com.ken.android.app.novel.covid19.report.repository.bean.Properties

@Deprecated(" replace to com.ken.android.app.novel.covid19.report.ui.map.cluster.TWMaskClusterMarkerManager")
class CustomInfoWindowAdapter(private var context: Context) : GoogleMap.InfoWindowAdapter {
    companion object{
        const val TAG = "InfoWindow"
    }

    override fun getInfoContents(marker: Marker?): View? {
        marker?:return null
        val propertyJson = marker.snippet
        if(propertyJson == null || propertyJson == ""){
            return null
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = DataBindingUtil.inflate<WidgetMapCustomInfoWindowBinding>(inflater, R.layout.widget_map_custom_info_window, null, true)

        val gson = Gson();
        val feature = gson.fromJson(propertyJson, Feature::class.java)

        binding.properties = feature.properties
        binding.executePendingBindings()

        return binding.root
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}