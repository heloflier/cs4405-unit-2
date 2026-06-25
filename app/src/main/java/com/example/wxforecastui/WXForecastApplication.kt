package com.example.wxforecastui

import android.app.Application
import com.google.android.material.color.DynamicColors

class WXForecastApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}