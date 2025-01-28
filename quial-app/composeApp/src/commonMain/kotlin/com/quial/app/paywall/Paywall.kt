package com.quial.app.paywall

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.navigator.Navigator
import com.mmk.kmprevenuecat.purchases.ui.Paywall
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import com.quial.app.navigation.RootAppDestination
import com.quial.app.utils.isAndroid
import dev.gitlive.firebase.analytics.FirebaseAnalytics

@Composable
fun SubscriptionPaywall(onDismiss: () -> Unit,
                        listener: PaywallListener? = null,
                        analytics: FirebaseAnalytics,
                        navigator: Navigator?,
                        paywallHolder: PaywallConfigStateHolder) {
    //In ios no need for fullscreen dialog,in android we add extra full screen dialog

    val previousScreenFeed = navigator?.items?.contains(RootAppDestination.Feed)
    val softPaywallBoolean = remember { mutableStateOf(false) }
    val paywallConfigMap by paywallHolder.paywallMap.collectAsState()

    if (previousScreenFeed == true) {
        softPaywallBoolean.value = paywallConfigMap["feed"]
            ?.contains("true") ?: false
    } else {
        softPaywallBoolean.value = paywallConfigMap["onboarding"]
            ?.contains("true") ?: false
    }

    if (isAndroid()) {
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismiss
        ) {
            // Write ktor server to remote control this
            // and add it to the app backend at 'true'
            Surface(modifier = Modifier.fillMaxSize()) {
                Paywall(
                    shouldDisplayDismissButton = softPaywallBoolean.value,
                    onDismiss = onDismiss,
                    listener = listener
                )
            }
        }
    } else {
        Surface(modifier = Modifier.fillMaxSize()) {
            Paywall(
                shouldDisplayDismissButton = softPaywallBoolean.value,
                onDismiss = onDismiss,
                listener = listener
            )
        }
    }
    analytics.logEvent("paywall_view")
}