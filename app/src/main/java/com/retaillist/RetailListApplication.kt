package com.retaillist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RetailListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}