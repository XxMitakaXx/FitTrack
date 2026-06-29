package com.example.fittrack

import android.app.Application
import com.example.fittrack.di.initKoin
import org.koin.android.ext.koin.androidContext

class FitTrackApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FitTrackApp)
        }
    }
}