package com.quial.app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.analytics.FirebaseAnalytics
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.main.App

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppContext.setUp(this@MainActivity.applicationContext)

        val color =  Color(125, 184, 107)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        installSplashScreen()

        val analytics = FirebaseAnalytics.getInstance(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(color.toArgb(), color.toArgb()),
            navigationBarStyle = SystemBarStyle.light(color.toArgb(), color.toArgb())
        )

        setContent {
            onApplicationStartPlatformSpecific()
            App(dataHolder = remember(applicationContext) {
                DataStoreStateHolder(createDataStore(applicationContext))
            })
        }
    }
}



@Preview
@Composable
fun AppAndroidPreview() {
    // App()
}