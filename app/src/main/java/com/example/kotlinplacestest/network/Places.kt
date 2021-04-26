package com.example.kotlinplacestest.network

import android.content.Context
import android.location.Location
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

import org.json.JSONObject

class Places(
    place: String? = "",
    context: Context?,
    searchForPlacesInterface: SearchForPlacesInterface?,
    location: Location
) {
    private val thisPlace: String? = place
    private val thisContext: Context? = context
    private val thisInterface: SearchForPlacesInterface? = searchForPlacesInterface
    private val TAG = Places::class.java.simpleName
    private val thisLocation: Location? = location

    fun searchForPlaces() {
        /*if (place!!.isEmpty()) {
            return
        }*/
        val queue = VolleySingleton.getInstance(thisContext!!)!!.requestQueue
        val url =
            Endpoints.PLACES_URL + "&location=" + thisLocation!!.latitude + "," + thisLocation.longitude + thisPlace
        Log.d(TAG, url)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject -> thisInterface!!.loadPlacesData(response.toString()) }) { }
        queue.add(jsonObjectRequest)
    }

    interface SearchForPlacesInterface {
        fun loadPlacesData(data: String?)
    }
}