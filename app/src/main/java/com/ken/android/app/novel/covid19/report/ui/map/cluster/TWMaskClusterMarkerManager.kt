package com.ken.android.app.novel.covid19.report.ui.map.cluster

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import com.google.maps.android.collections.MarkerManager
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.WidgetMapCustomInfoWindowBinding
import com.ken.android.app.novel.covid19.report.repository.bean.Feature

class TWMaskClusterMarkerManager(private var context: Context, private var map : GoogleMap): MarkerManager(map){

    private var mHandler = Handler(Looper.getMainLooper())
    private var currentDisplayInfoWindowMark : Marker? = null
    private var onInfoWindowClickListener : GoogleMap.OnInfoWindowClickListener? = null

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

    override fun onInfoWindowClick(marker: Marker?) {
        hideCurrentDisplayInfoWindow()
        onInfoWindowClickListener?.onInfoWindowClick(marker)
        super.onInfoWindowClick(marker)
    }

    fun setOnInfoWindowClickListener(onInfoWindowClickListener : GoogleMap.OnInfoWindowClickListener){
        this.onInfoWindowClickListener = onInfoWindowClickListener
    }



    override fun onMarkerClick(marker: Marker?): Boolean {
        val position = marker?.position?:return true
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        marker.showInfoWindow()
        currentDisplayInfoWindowMark = marker
        mHandler.removeCallbacks(delayHideCurrentDisplayInfoWindowRunnable)
        mHandler.postDelayed(delayHideCurrentDisplayInfoWindowRunnable, 30000)
        return super.onMarkerClick(marker)
    }





    private fun hideCurrentDisplayInfoWindow(){
        mHandler.removeCallbacks(delayHideCurrentDisplayInfoWindowRunnable)
        currentDisplayInfoWindowMark?.hideInfoWindow()
    }

    private var delayHideCurrentDisplayInfoWindowRunnable = Runnable {
        hideCurrentDisplayInfoWindow()
    }

    fun destroy(){
        mHandler.removeCallbacks(delayHideCurrentDisplayInfoWindowRunnable)
        currentDisplayInfoWindowMark = null
    }
}