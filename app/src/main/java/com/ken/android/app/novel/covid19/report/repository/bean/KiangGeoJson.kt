package com.ken.android.app.novel.covid19.report.repository.bean

import java.io.Serializable

data class KiangGeoJson(
    val features: List<Feature>,
    val type: String
)

class Feature : Serializable {
    lateinit var geometry : Geometry
    lateinit var properties : Properties
    lateinit var type : String
}

class Geometry : Serializable{
    lateinit var coordinates: List<Double>
    lateinit var type: String
}

class Properties : Serializable{
    lateinit var address: String
    lateinit var available: String
    lateinit var county: String
    lateinit var cunli: String
    lateinit var custom_note: String
    lateinit var id: String
    var mask_adult : Int = 0
    var mask_child : Int = 0
    lateinit var name: String
    lateinit var note: String
    lateinit var phone: String
    lateinit var service_periods: String
    lateinit var town: String
    lateinit var updated: String
    lateinit var website: String
}