package com.ken.android.app.novel.covid19.report.repository.bean

import java.text.SimpleDateFormat
import java.util.*

/**
 * 原諒我欄位通通轉 string，因為被搞到，api 有時會是int，有時會是null，有時會string，也許在調整吧
 * **/
class GlobalTotalCase{
    var active = ""
    var cases = ""
    var deaths = ""
    var recovered = ""
    var todayCases = ""
    var todayDeaths = ""
    var critical = ""
    var updated = -1L

    fun getDateFormatted() : String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(updated <= 0 ){
            return "unknown"
        }
        return formatter.format(Date(updated))
    }
}
