package com.example.kotlinplacestest.gson

data class Results(
    val business_status: String?,
    val formatted_address: String?,
    val geometry: Geometry?,
    val icon: String?,
    val name: String?,
    val opening_hours: OpeningHours?,
    val photos: List<Photos?>?,
    val place_id: String?,
    val plus_code: PlusCode?,
    val price_level: Int?,
    val rating: Float?,
    val reference: String?,
    val types: List<String?>?,
    val user_ratings_total: Int?
)

data class Geometry(
    val location: Location?
)

data class Location(
    val lat: Float?,
    val lng: Float?
)

data class Viewport(
    val region: String?,
    val regionInfo: RegionInfo?
)

data class RegionInfo(
    val lat: Float?,
    val lng: Float?
)

data class OpeningHours(
    val open_now: Boolean?
)

data class Photos(
    val height: Int?,
    val html_attributions: List<String?>?,
    val photo_reference: String?,
    val width: Int?
)

data class PlusCode(
    val compound_code: String?,
    val global_code: String?
)