package com.example.kotlinplacestest.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

import org.json.JSONObject

class Places {
    fun searchForPlaces(
        place: String?,
        context: Context?,
        searchForBooksInterface: SearchForPlacesInterface?
    ) {
        /*if (place!!.isEmpty()) {
            return
        }*/
        val queue = VolleySingleton.getInstance(context)!!.requestQueue
        val url = Endpoints.PLACES_URL + place
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject -> searchForBooksInterface!!.loadData(response.toString()) }) { }
        queue.add(jsonObjectRequest)
    }

    interface SearchForPlacesInterface {
        fun loadData(data: String?)
    }
}