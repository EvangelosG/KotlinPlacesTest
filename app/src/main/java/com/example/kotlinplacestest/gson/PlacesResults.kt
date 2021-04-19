package com.example.kotlinplacestest.gson

data class PlacesResults (
    val html_attributions : List<String?>?,
    val next_page_token : String?,
    val results : List<Results?>?
)