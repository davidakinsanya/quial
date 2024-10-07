package com.quial.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.paywall.SubscriptionPaywall
import com.quial.app.screen.auth.AuthUiHelperButtonsAndFirebaseAuth
import com.quial.app.screen.onboarding.comps.OnboardingScreen
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.getUiStateHolder

interface RootAppDestination {

    object Onboarding: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            OnboardingScreen(
                modifier = Modifier,
                uiStateHolder = getUiStateHolder<OnboardingUiStateHolder>(),
                dataStateHolder = getUiStateHolder<DataStoreStateHolder>(),
                onNavigateMain = {}
            )
        }
    }

    object Auth: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            AuthUiHelperButtonsAndFirebaseAuth(
                modifier = Modifier,
                onFirebaseResult = {}
            )
        }
    }

    object Paywall: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow

            SubscriptionPaywall(
                onDismiss = {}, // uiStateHolder::onDismissSubscriptionPlansView,
                listener = object: PaywallListener {
                    override fun onPurchaseCompleted(customerInfo: CustomerInfo?) {
                        super.onPurchaseCompleted(customerInfo)
                        // uiStateHolder.onSubscriptionPurchaseCompleted()
                    }

                    override fun onPurchaseError(error: String?) {
                        super.onPurchaseError(error)
                        // uiStateHolder.onSubscriptionPurchaseError(error)
                    }


                    override fun onRestoreCompleted(customerInfo: CustomerInfo?) {
                        super.onRestoreCompleted(customerInfo)
                        // uiStateHolder.onSubscriptionPurchaseCompleted()
                    }

                    override fun onRestoreError(error: String?) {
                        super.onRestoreError(error)
                        //uiStateHolder.onSubscriptionPurchaseError(error)
                    }
                }
            )
        }
    }



}

@Composable
fun RootAppNavigation(startDestination: RootAppDestination) {
    Navigator(screen = startDestination as Screen)
}