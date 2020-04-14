package com.ken.android.app.novel.covid19.report.repository.bean

import java.text.SimpleDateFormat
import java.util.*

class Country {
    var active = -1
    var cases = ""
    var casesPerOneMillion = ""
    var country = ""
    var countryInfo =
        CountryInfo()
    var critical =  ""
    var deaths =  ""
    var deathsPerOneMillion =  ""
    var recovered =  ""
    var todayCases =  ""
    var todayDeaths =  ""
    var updated = -1L

    fun getDateFormatted() : String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(updated <= 0 ){
            return "unknown"
        }
        return formatter.format(Date(updated))
    }
}

