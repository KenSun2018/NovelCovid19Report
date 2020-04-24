package com.ken.android.app.novel.covid19.report.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.clustering.ClusterManager
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.FragmentTwMaskMapBinding
import com.ken.android.app.novel.covid19.report.repository.bean.Feature
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.ui.map.cluster.TWMaskClusterItem
import com.ken.android.app.novel.covid19.report.ui.map.cluster.TWMaskClusterMarkerManager
import com.ken.android.app.novel.covid19.report.ui.map.cluster.TWMaskClusterRender
import com.ken.android.app.novel.covid19.report.ui.news.FragmentNews


class FragmentTWMaskMap : Fragment(), OnMapReadyCallback {
    companion object{
        private const val TAG = "FragmentTWMaskMap"

        private val TAIWAN = LatLng(23.97, 120.98);
    }
    private lateinit var mMap: GoogleMap

    private lateinit var viewModel : FragmentTWMaskViewModel
    private lateinit var binding : FragmentTwMaskMapBinding
    private lateinit var mMapFragment : SupportMapFragment
    private var currentDisplayInfoWindowMark : Marker? = null


    private var myLatLng : LatLng? = null

    private lateinit var mClusterManager : ClusterManager<TWMaskClusterItem>
    private lateinit var mClusterMarkerManager : TWMaskClusterMarkerManager
    private lateinit var mClusterRender : TWMaskClusterRender

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentTwMaskMapBinding>(inflater, R.layout.fragment_tw_mask_map, container, false)
        mMapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        viewModel = FragmentTWMaskViewModelRxImpl()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mMapFragment.getMapAsync(this)
        viewModel.getGeoJsonLiveData().observe(requireActivity(), Observer {
            addAllMarkers(it)
        })
    }

    private fun addAllMarkers(it : KiangGeoJson){
        val features = it.features
        val gson = Gson();
        for(feature in features){
            val geo = feature.geometry
            val coordinate = geo.coordinates

            if(coordinate.isEmpty()){
                continue
            }
            if(coordinate[0] > 0 && coordinate[1] > 0){
                val latLng = LatLng(coordinate[1], coordinate[0])
                val pointProperties = feature.properties
                val featrueJson = gson.toJson(feature)

                val clusterItem =
                    TWMaskClusterItem.Builder(latLng)
                        .title("${pointProperties.name}")
                        .snippet(featrueJson)
                        .build()
                mClusterManager.addItem(clusterItem)
            }
        }
        mClusterManager.cluster()
    }




    private fun setupEvent(){
        mClusterMarkerManager.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener{
            override fun onInfoWindowClick(marker: Marker?) {

                val snippet = marker?.snippet?:return
                val gson = Gson()
                val feature = gson.fromJson(snippet, Feature::class.java)

                val intent = Intent(requireActivity(), ActivityPharmacyDetail::class.java)
                intent.putExtra(ActivityPharmacyDetail.BUNDLE_KEY_FEATURE, feature)
                if(myLatLng != null){
                    val latLng = myLatLng!!
                    intent.putExtra(ActivityPharmacyDetail.BUNDLE_KEY_MY_LATITUDE, latLng.latitude)
                    intent.putExtra(ActivityPharmacyDetail.BUNDLE_KEY_MY_LONGITUDE, latLng.longitude)
                }

                startActivity(intent)
            }
        })

        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)
    }



    override fun onMapReady(map: GoogleMap) {
        this.mMap = map
        mClusterMarkerManager = TWMaskClusterMarkerManager(requireContext(), map)
        mClusterManager = ClusterManager(requireContext(), map, mClusterMarkerManager)
        mClusterRender = TWMaskClusterRender(requireContext(), map, mClusterManager);
        mClusterManager.renderer = mClusterRender
        mClusterRender.minClusterSize = 10

        setupEvent()

        map.isMyLocationEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        val location = getLocation()
        viewModel.loadMaskMapData()
        if(location == null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TAIWAN, 2f))
            return
        }
        val targetLatLng = LatLng(location.latitude, location.longitude)
        myLatLng = targetLatLng

        mMap.addMarker(MarkerOptions()
            .icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_my_position_48dp))
            .position(targetLatLng)
            .title("我的位置"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, 15f))
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() : Location?{
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if(location == null){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        if(location == null){
            val criteria = Criteria() //資訊提供者選取標準
            val bestProvider = locationManager.getBestProvider(criteria, true);    //選擇精準度最高的提供者

            if(bestProvider != null){
                location = locationManager.getLastKnownLocation(bestProvider);
            }
        }

        return location
    }

    override fun onDestroy() {
        super.onDestroy()
        mClusterMarkerManager.destroy()
        myLatLng = null
        currentDisplayInfoWindowMark = null
        mClusterManager.renderer = null
    }
}