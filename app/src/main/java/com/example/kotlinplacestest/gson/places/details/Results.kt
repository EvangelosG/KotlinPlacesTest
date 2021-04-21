package com.example.kotlinplacestest.gson.places.details

data class Results(
        val address_components: List<AddressComponents?>?,
        val adr_address: String?,
        val business_status: String?,
        val formatted_address: String?,
        val formatted_phone_number: String?,
        val geometry: Geometry?,
        val icon: String?,
        val international_phone_number: String?,
        val name: String?,
        val opening_hours: OpeningHours?,
        val photos: List<Photos?>?,
        val place_id: String?,
        val plus_code: PlusCode?,
        val price_level: Int?,
        val rating: Float?,
        val reference: String?,
        val reviews: List<Reviews?>?,
        val types: List<String?>?,
        val url: String?,
        val user_ratings_total: Int?,
        val utc_offset: Int?,
        val vicinity: String?,
        val website: String?,
)

data class AddressComponents(
        val long_name: Int?,
        val short_name: Int?,
        val types: List<String?>?
)

data class Geometry(
        val location: Location?,
        val viewport: Viewport
)

data class Location(
        val lat: Float?,
        val lng: Float?
)

data class Viewport(
        val northeast: NorthEast?,
        val southwest: SouthWest?
)

data class NorthEast(
        val lat: Float?,
        val lng: Float?
)

data class SouthWest(
        val lat: Float?,
        val lng: Float?
)

data class OpeningHours(
        val open_now: Boolean?,
        val periods: List<Periods?>?,
        val weekday_text: List<String?>?
)

data class Periods(
        val close: Close?,
        val open: Open?
)

data class Close(
        val day: Int?,
        val time: Int?
)

data class Open(
        val day: Int?,
        val time: Int?
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

data class Reviews(
        val author_name: String?,
        val author_url: String?,
        val language: String?,
        val profile_photo_url: String?,
        val rating: Int?,
        val relative_time_description: String?,
        val text: String?,
        val time: Int?
)