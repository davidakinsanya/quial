package com.quial.app

import android.app.Activity
import android.app.Application
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

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

internal object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if(::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}

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

actual fun inAppRating() {
        val context: Context = AppContext.get()
        val reviewManager = ReviewManagerFactory.create(context)
        context.applicationContext
            ?.let { activity ->
                val request = reviewManager.requestReviewFlow()
                request.addOnCompleteListener { requestInfo ->
                    if (requestInfo.isSuccessful) {
                        // Launch the in-app review flow
                        val reviewInfo = requestInfo.result
                        val flow = reviewManager.launchReviewFlow(Activity(), reviewInfo)
                        flow.addOnCompleteListener { _ ->
                            // Review flow completed
                        }
                    } else {
                        // Handle the error
                    }
                }
            }
}