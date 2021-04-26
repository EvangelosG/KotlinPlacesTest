package com.example.kotlinplacestest

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class ApplicationClass : MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
