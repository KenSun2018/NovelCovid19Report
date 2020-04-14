package com.ken.android.app.novel.covid19.report.ui.map


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.ActivityPharmacyDetailBinding
import com.ken.android.app.novel.covid19.report.repository.bean.Feature



class ActivityPharmacyDetail : AppCompatActivity(), OnMapReadyCallback {
    companion object{
        private const val TAG = "PharmacyDetail"
        const val BUNDLE_KEY_FEATURE = "bundle_key_feature"
        const val BUNDLE_KEY_MY_LATITUDE = "bundle_key_my_latitude"
        const val BUNDLE_KEY_MY_LONGITUDE = "bundle_key_my_longitude"
    }

    private lateinit var binding : ActivityPharmacyDetailBinding
    private lateinit var mMapFragment : SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var feature: Feature

    private var myLatLng : LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPharmacyDetailBinding>(this, R.layout.activity_pharmacy_detail)
        if(!intent.hasExtra(BUNDLE_KEY_FEATURE)){
            showDescriptionErrorAndFinish()
            return
        }

        val extra = intent.getSerializableExtra(BUNDLE_KEY_FEATURE)
        if(extra == null || extra !is Feature){
            showDescriptionErrorAndFinish()
            return
        }


        if(intent.hasExtra(BUNDLE_KEY_MY_LATITUDE) && intent.hasExtra(BUNDLE_KEY_MY_LONGITUDE)){
            val latitude = intent.getDoubleExtra(BUNDLE_KEY_MY_LATITUDE, -1.0)
            val longitude = intent.getDoubleExtra(BUNDLE_KEY_MY_LONGITUDE, -1.0)
            if(latitude > 0 && longitude > 0){
                myLatLng = LatLng(latitude, longitude)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = extra.properties.name

        mMapFragment = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        binding.feature = extra
        this.feature = extra
        binding.progress.visibility = View.VISIBLE
        mMapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDescriptionErrorAndFinish(){
        Toast.makeText(this, "系統錯誤，無法解析商店資訊，請新試一次，如仍有疑慮，請退出App後再試", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onMapReady(map: GoogleMap) {
        this.mMap = map
        val latLng = LatLng(feature.geometry.coordinates[1], feature.geometry.coordinates[0])
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
        mMap.addMarker(MarkerOptions().position(latLng))
        binding.progress.visibility = View.GONE
        val servicePeriod = feature.properties.service_periods
        val list = servicePeriod.chunked(1)
        Log.e(TAG, " list = $list, list.size = ${list.size}")
        binding.businessHoursLayout.servicePeriods = list
        binding.pharmacyInfoLayout.properties = feature.properties

        setupEvent()

    }

    private fun setupEvent(){
        binding.pharmacyInfoLayout.pharmacyPhoneIcon.isClickable = true
        binding.pharmacyInfoLayout.pharmacyPhoneIcon.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${feature.properties.phone}"))
                startActivity(dialIntent)
            }
        })
        binding.pharmacyInfoLayout.pharmacyPhoneNumberFieldValue.isClickable = true
        binding.pharmacyInfoLayout.pharmacyPhoneNumberFieldValue.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${feature.properties.phone}"))
                startActivity(dialIntent)
            }
        })

        binding.pharmacyInfoLayout.pharmacyAddressFieldValue.isClickable = true
        binding.pharmacyInfoLayout.pharmacyAddressFieldValue.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                intentToNav()
            }
        })
        binding.pharmacyInfoLayout.pharmacyNavigationIcon.isClickable = true
        binding.pharmacyInfoLayout.pharmacyNavigationIcon.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                intentToNav()
            }
        })


    }

    private fun intentToNav(){
        val latitude = feature.geometry.coordinates[1]
        val longitude = feature.geometry.coordinates[0]
        val gmmIntentUri =
            Uri.parse("google.navigation:q=$latitude,$longitude&mode=w")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}