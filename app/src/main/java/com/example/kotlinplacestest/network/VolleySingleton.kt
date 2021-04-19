package com.example.kotlinplacestest.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context?) {
    val requestQueue: RequestQueue = Volley.newRequestQueue(context!!.applicationContext)

    companion object {
        @Volatile
        private var instance: VolleySingleton? = null
        fun getInstance(context: Context?): VolleySingleton? {
            if (instance == null) {
                synchronized(VolleySingleton::class.java) {
                    instance = VolleySingleton(context!!.applicationContext)
                }
            }
            return instance
        }
    }
}