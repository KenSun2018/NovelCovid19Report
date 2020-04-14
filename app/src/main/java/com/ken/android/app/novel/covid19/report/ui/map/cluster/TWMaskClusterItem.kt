package com.ken.android.app.novel.covid19.report.ui.map.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class TWMaskClusterItem private constructor(builder : Builder) : ClusterItem {

    private var snippet : String = ""
    private var title : String = ""
    private var latLng : LatLng

    init {
        snippet = builder.snippet
        title = builder.title
        latLng = builder.latLng
    }


    override fun getSnippet(): String {
        return snippet
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPosition(): LatLng? {
        return latLng
    }

    class Builder (var latLng: LatLng){
        var snippet : String = ""
            private set

        var title : String = ""
            private set

        fun snippet(snippet : String) : Builder {
            this.snippet = snippet
            return this
        }

        fun title(title : String) : Builder {
            this.title = title
            return this
        }

        fun build() : TWMaskClusterItem {
            return TWMaskClusterItem(
                this
            )
        }
    }


}