package com.ken.android.app.novel.covid19.report.utils


import com.ken.android.app.novel.covid19.report.BuildConfig

object Log {
    fun i(tag : String, msg : String){
        if(BuildConfig.IS_APP_DEBUG){
            android.util.Log.i(tag, msg)
        }
    }

    fun d(tag : String, msg : String){
        if(BuildConfig.IS_APP_DEBUG){
            android.util.Log.d(tag, msg)
        }
    }

    fun w(tag : String, msg : String){
        if(BuildConfig.IS_APP_DEBUG){
            android.util.Log.w(tag, msg)
        }
    }

    fun e(tag : String, msg : String){
        if(BuildConfig.IS_APP_DEBUG){
            android.util.Log.e(tag, msg)
        }
    }
}