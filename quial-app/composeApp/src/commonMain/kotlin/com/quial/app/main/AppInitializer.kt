package com.quial.app.main

import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmprevenuecat.purchases.LogLevel
import com.mmk.kmprevenuecat.purchases.Purchases
import com.quial.app.di.appModules
import com.quial.app.utils.isAndroid
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import secrets.BuildConfig


object AppInitializer {

    fun initialize(isDebug: Boolean = false,
                   onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
            onApplicationStart()
        }

        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(if (isAndroid()) BuildConfig.REVENUECAT_API_KEY_ANDROID else BuildConfig.REVENUECAT_API_KEY_IOS)
    }

    private fun KoinApplication.onApplicationStart() {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                println("onNewToken: $token")
            }
        })
        GoogleAuthProvider.create(GoogleAuthCredentials(serverId = BuildConfig.GOOGLE_AUTH))
    }
}