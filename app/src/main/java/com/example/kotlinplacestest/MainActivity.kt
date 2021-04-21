package com.example.kotlinplacestest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import com.example.kotlinplacestest.gson.places.PlacesResults
import com.example.kotlinplacestest.network.Places
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity(), Places.SearchForPlacesInterface {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val chickenPlaces = Places("", this, this)
        chickenPlaces.searchForPlaces()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun loadData(data: String?) {
        val gson : Gson = GsonBuilder().create()
        val pResults : PlacesResults = gson.fromJson(data, PlacesResults::class.java)
        for (Results in pResults.results!!) {
            Log.d(TAG, Results!!.formatted_address!!)
        }
    }
}