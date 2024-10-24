package com.quial.app

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.android.play.core.review.ReviewManagerFactory
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.quial.app.data.datastore.DATA_STORE_FILE_NAME
import com.quial.app.data.datastore.createDataStore
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context
            .filesDir
            .resolve(DATA_STORE_FILE_NAME)
            .absolutePath
    }
}

actual class Growth(
    private val context: Context
) {
    actual fun inAppRating() {
        val reviewManager = ReviewManagerFactory.create(context)
        context.applicationContext
            ?.let { activity ->
                val request = reviewManager.requestReviewFlow()
                request.addOnCompleteListener { requestInfo ->
                    if (requestInfo.isSuccessful) {
                        // Launch the in-app review flow
                        val reviewInfo = requestInfo.result
                        val flow = reviewManager.launchReviewFlow(activity as Activity, reviewInfo)
                        flow.addOnCompleteListener { _ ->
                            // Review flow completed
                        }
                    } else {
                        // Handle the error
                    }
                }
            }
    }
}

actual fun sharedPlatformModule(): Module = module {
   singleOf(::Growth)
}