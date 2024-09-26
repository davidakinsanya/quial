package com.quial.app

import android.os.Build
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import okhttp3.OkHttpClient

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun onApplicationStartPlatformSpecific() {
    NotifierManager.initialize(
        NotificationPlatformConfiguration.Android(
            showPushNotification = true,
            notificationIconResId = 0
        )
    )
}

actual fun getHttpClient(): HttpClientEngine {
    return OkHttp.create()
}

actual fun getHttpConfig(): HttpClientConfig<*> {
   return HttpClientConfig<OkHttpConfig>()
}