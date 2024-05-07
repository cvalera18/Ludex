package com.cvalera.ludex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Ludex: Application() {
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}