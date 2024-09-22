package com.quial.app.main

import com.mmk.kmprevenuecat.purchases.LogLevel
import com.mmk.kmprevenuecat.purchases.Purchases
import com.quial.app.di.appModules
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import secrets.BuildConfig


object AppInitializer {

    fun initialize(isDebug: Boolean = false,
                   onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
            // onApplicationStart()
        }

        Purchases.logLevel = LogLevel.DEBUG
        //Purchases.configure(if (isAndroid()) BuildConfig.REVENUECAT_API_KEY_ANDROID else BuildConfig.REVENUECAT_API_KEY_IOS)
    }
}