package com.ken.android.app.novel.covid19.report.ui.map.cluster

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.ken.android.app.novel.covid19.report.repository.bean.Feature
import com.ken.android.app.novel.covid19.report.repository.bean.Properties


class TWMaskClusterRender(context: Context, map : GoogleMap, clusterManager: ClusterManager<TWMaskClusterItem>) : DefaultClusterRenderer<TWMaskClusterItem>(context, map, clusterManager) {

    companion object{
        const val TAG = "TWMaskClusterRender"
    }

    init {
        setAnimation(false)
    }

    override fun onBeforeClusterItemRendered(
        item: TWMaskClusterItem?,
        markerOptions: MarkerOptions?
    ) {

        val gson = Gson();
        val snippet = item?.snippet

        if(snippet != null){
            val feature = gson.fromJson(snippet, Feature::class.java)
            val alpha = getAlphaByMaskAmount(feature.properties)
            markerOptions?.alpha(alpha)
            markerOptions?.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        }
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    private fun isMarkVisibleByMaskAmount(properties: Properties) : Boolean{
        val adultMask = properties.mask_adult
        val childMask = properties.mask_child

        if(adultMask == 0 && childMask == 0){
            return false
        }

        return true
    }

    private fun getAlphaByMaskAmount(properties : Properties) : Float{
        val adultMask = properties.mask_adult
        val childMask = properties.mask_child

        if(adultMask > 0 && childMask > 0){
            return 1.0f
        }

        if(adultMask > 0){
            return 0.5f
        }

        if(childMask > 0){
            return 0.5f
        }

        return 0.1f
    }


}