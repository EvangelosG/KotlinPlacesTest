package com.example.kotlinplacestest.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions {
    private fun startLocationPermissionRequest(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSIONS_REQUEST_CODE
        )
    }

    companion object {
        const val LOCATION_PERMISSIONS_REQUEST_CODE = 100
        fun shouldCheckPermissions(): Boolean {
            return Build.VERSION.SDK_INT >= 23
        }

        fun checkLocationPermissions(context: Context?): Boolean {
            return if (!shouldCheckPermissions()) {
                true
            } else ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun userHasGrantedPermissions(context: Context?): Boolean {
            return if (!shouldCheckPermissions()) {
                true
            } else ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
