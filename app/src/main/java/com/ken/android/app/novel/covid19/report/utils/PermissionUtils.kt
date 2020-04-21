package com.ken.android.app.novel.covid19.report.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {


    private val REQUIRE_PERMISSION_LIST = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun getDeniedPermissions(context : Context) : Array<String>{
        val requirePermissionList = ArrayList<String>()
        for(requirePermission in REQUIRE_PERMISSION_LIST){
            val permission = ActivityCompat.checkSelfPermission(context, requirePermission)
            if(permission == PackageManager.PERMISSION_DENIED){
                requirePermissionList.add(requirePermission)
            }
        }
        val array = arrayOfNulls<String>(requirePermissionList.size)
        return requirePermissionList.toArray(array)
    }
}