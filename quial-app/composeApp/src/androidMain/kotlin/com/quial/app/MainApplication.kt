package com.quial.app

import android.app.Application
import com.quial.app.main.AppInitializer
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AppInitializer.initialize {
            androidContext(this@MainApplication)
        }
    }
}