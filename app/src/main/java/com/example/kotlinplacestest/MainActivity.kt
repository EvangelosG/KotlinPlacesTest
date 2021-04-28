package com.example.kotlinplacestest

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinplacestest.adapters.PlaceAdapter
import com.example.kotlinplacestest.gson.places.PlacesResults
import com.example.kotlinplacestest.gson.places.Results
import com.example.kotlinplacestest.helpers.LocManager
import com.example.kotlinplacestest.network.Places
import com.example.kotlinplacestest.util.Permissions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Places.SearchForPlacesInterface,
    LocManager.LocationListener {

    private lateinit var locationManager: LocManager
    private val SPAN_COUNT = 1

    val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = LocManager.getInstance(this)!!
        locationManager.addListener(this)
        setStatusBar()
        fadeInBackground()
    }

    private fun setStatusBar() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.popeyes_orange)
    }

    private fun fadeInBackground() {
        var fadeIn : Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeIn.duration = 1500
        fadeIn.fillAfter = true
        background.startAnimation(fadeIn)
    }

    private fun setupGridView(places : List<Results?>) {
        gridView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        val placesAdapter = PlaceAdapter()
        gridView.adapter = placesAdapter
        placesAdapter.setList(places)
        gridView.visibility = View.INVISIBLE
        val fadeIn : Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeIn.duration = 3000
        fadeIn.fillAfter = true
        gridView.startAnimation(fadeIn)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeListener(this)
    }

    override fun onLocationChange(location: Location?) {
        Log.d(
            TAG,
            "location: " + location!!.latitude.toString() + " : " + location.longitude.toString()
        )

        val places = Places("", this, this, location)
        places.searchForPlaces()
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }*/

    override fun loadPlacesData(data: String?) {
        val gson: Gson = GsonBuilder().create()
        val pResults: PlacesResults = gson.fromJson(data, PlacesResults::class.java)
        for (Results in pResults.results!!) {
            Log.d(TAG, Results!!.toString())
        }
        setupGridView(pResults.results)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == Permissions.LOCATION_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.d(TAG, "interaction cancelled")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    Log.d(TAG, "location access granted")
                    LocManager.getInstance(this)!!.requestLocationUpdates(this)
                }
                else -> {
                    Log.w(TAG, "User perm denied or interaction cancelled")
                }
            }
        }
    }
}