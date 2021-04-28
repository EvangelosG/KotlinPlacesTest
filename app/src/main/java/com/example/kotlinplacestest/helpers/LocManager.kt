package com.example.kotlinplacestest.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.kotlinplacestest.R
import com.example.kotlinplacestest.util.Permissions
import com.example.kotlinplacestest.util.Permissions.Companion.userHasGrantedPermissions
import com.google.android.gms.location.*
import java.util.*

class LocManager {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var listeners: MutableList<LocationListener>? = null
    val TAG = LocManager::class.java.simpleName
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            lastKnownLocation = locationResult.lastLocation
            for (listener in listeners!!) {
                listener.onLocationChange(lastKnownLocation)
            }

            //Un-comment for one location update per app run
            stopLocationUpdates()
            Log.d(TAG, "removing location updates...")
        }
    }

    @SuppressWarnings("MissingPermission")
    fun updateLatestLocation(context: Context) {
        getFusedLocationClient(context)!!.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    lastKnownLocation = task.result
                    Log.d(TAG, "Location Updated: " + lastKnownLocation.toString())
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(context: Context) {
        getFusedLocationClient(context)!!.requestLocationUpdates(
            getLocationRequest(),
            locationCallback,
            null
        )
    }

    private fun getFusedLocationClient(context: Context): FusedLocationProviderClient? {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }
        return fusedLocationClient
    }

    private fun stopLocationUpdates() {
        if (fusedLocationClient != null) {
            fusedLocationClient!!.flushLocations()
            fusedLocationClient!!.removeLocationUpdates(locationCallback)
        }
    }

    private fun getLocationRequest(): LocationRequest {
        if (locationRequest == null) {
            locationRequest = LocationRequest()
            locationRequest!!.priority = LocationRequest.PRIORITY_LOW_POWER
            locationRequest!!.interval = (60 * 60 * 1000).toLong()
            locationRequest!!.fastestInterval = (60 * 1000).toLong()
        }
        return locationRequest!!
    }

    fun startLocationPermissionRequest(activity: Activity?) {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Permissions.LOCATION_PERMISSIONS_REQUEST_CODE
        )
    }

    fun checkAndWarnAboutLocationServices(activity: Activity) {
        if (!isLocationEnabled(activity)) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(activity.resources.getString(R.string.location_services_disabled))
                .setTitle(activity.resources.getString(R.string.warning))
                .setPositiveButton(R.string.settings) { dialog: DialogInterface?, which: Int ->
                    activity.startActivity(
                        Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                    )
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        } else if (!isPermissionAvailable(activity)) {
            startLocationPermissionRequest(activity)
        } else {
            sInstance!!.requestLocationUpdates(activity)
        }
    }

    fun addListener(listener: LocationListener) {
        listeners!!.add(listener)
    }

    fun removeListener(listener: LocationListener) {
        listeners!!.remove(listener)
    }

    interface LocationListener {
        fun onLocationChange(location: Location?)
    }

    companion object {
        const val METERS_TO_MILES = 0.000621371
        private var sInstance: LocManager? = null
        var lastKnownLocation: Location? = null
            private set

        fun getInstance(context: Context): LocManager? {
            if (sInstance == null) {
                synchronized(LocManager::class.java) {
                    sInstance = LocManager()
                    sInstance!!.listeners = ArrayList()
                    if (context !== context.applicationContext) {
                        if (!userHasGrantedPermissions(context.applicationContext)) {
                            sInstance!!.checkAndWarnAboutLocationServices(context as Activity)
                        } else {
                            sInstance!!.requestLocationUpdates(context.applicationContext)
                        }
                    }
                }
            }
            return sInstance
        }

        fun isPermissionAvailable(activity: Activity?): Boolean {
            return ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                locationMode = try {
                    Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
                } catch (e: SettingNotFoundException) {
                    e.printStackTrace()
                    return false
                }
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
                !TextUtils.isEmpty(locationProviders)
            }
        }
    }
}