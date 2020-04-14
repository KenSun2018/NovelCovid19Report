package com.ken.android.app.novel.covid19.report

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.tabs.TabLayoutMediator
import com.ken.android.app.novel.covid19.report.databinding.ActivityMainBinding

import com.ken.android.app.novel.covid19.report.ui.country.FragmentCountry
import com.ken.android.app.novel.covid19.report.ui.map.FragmentTWMaskMap
import com.ken.android.app.novel.covid19.report.ui.news.FragmentNews
import java.util.*
import kotlin.collections.ArrayList


/**
 * tw mask fragment visible condition
 * 1. tw
 * 2. has permission
 * **/

class MainActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "MainActivity"

        private val REQUIRE_PERMISSION_LIST = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        private const val PERMISSION_REQUEST_CODE = 100
    }


    private var viewPagerPageCount = 2
    private lateinit var binding : ActivityMainBinding

    private var isDisplayTwMaskMapFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val language = Locale.getDefault().country
        if(language.equals("tw", ignoreCase = true) && hasGooglePlay()){
            checkPermission(false)
        }else{
            setupViewPager()
        }
    }

    private fun hasGooglePlay() : Boolean{
        return when(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)){
            ConnectionResult.SUCCESS,
            ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED,
            ConnectionResult.SERVICE_UPDATING ->{
                true
            }
            else -> false
        }
    }


    private fun getPermissionDeniedList() : Array<String>{
        val requirePermissionList = ArrayList<String>()
        for(requirePermission in REQUIRE_PERMISSION_LIST){
            val permission = ActivityCompat.checkSelfPermission(this.applicationContext, requirePermission)
            if(permission == PackageManager.PERMISSION_DENIED){
                requirePermissionList.add(requirePermission)
            }
        }
        val array = arrayOfNulls<String>(requirePermissionList.size)
        return requirePermissionList.toArray(array)
    }

    private fun checkPermission(isOnActivityResult : Boolean){
        val deniedList = getPermissionDeniedList()
        if(deniedList.isNotEmpty()){
            if(isOnActivityResult){
                setupViewPager()
                return
            }
            ActivityCompat.requestPermissions( this, deniedList , PERMISSION_REQUEST_CODE )
            return
        }

        isDisplayTwMaskMapFragment = true
        viewPagerPageCount = 3
        setupViewPager()
    }

    private fun setupViewPager(){

        binding.mainViewpager.adapter = ViewPagerAdapter(this)
        binding.mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(isDisplayTwMaskMapFragment){
                    binding.mainViewpager.isUserInputEnabled = position != 2
                }else{
                    binding.mainViewpager.isUserInputEnabled = true
                }
            }
        })
        TabLayoutMediator(binding.mainTablayout, binding.mainViewpager, true,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = resources.getText(R.string.tab_title_country)
                    1 -> tab.text = resources.getText(R.string.tab_title_news)
                    2 -> {
                        if(isDisplayTwMaskMapFragment){
                            tab.text = resources.getText(R.string.tab_title_tw_mask_map)
                        }else{
                            throw Exception("unknown tab position")
                        }
                    }
                    else -> throw Exception("unknown tab position")
                }
            }).attach()

    }



    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = viewPagerPageCount
        override fun createFragment(position: Int): Fragment =

            when (position) {
                0 -> FragmentCountry()
                1 -> FragmentNews()
                2 -> {
                    if(isDisplayTwMaskMapFragment){
                        FragmentTWMaskMap()
                    }else{
                        throw Exception("unknown fragment position")
                    }
                }
                else -> throw Exception("unknown fragment position")
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            checkPermission(true)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                checkPermission(true)
            }else{
                setupViewPager()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}