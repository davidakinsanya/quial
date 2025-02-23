package com.quial.app.paywall

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import com.quial.app.data.datastore.DataStoreStateHolder
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import secrets.BuildConfig

@Composable
fun StripeCheckout(analytics: FirebaseAnalytics, dataHolder: DataStoreStateHolder) {
    val uriHandler = LocalUriHandler.current
    uriHandler.openUri(BuildConfig.STRIPE_TEST_CHECKOUT + dataHolder.getEmail().replace("@", "%40"))
    analytics.logEvent("paywall_view")
}