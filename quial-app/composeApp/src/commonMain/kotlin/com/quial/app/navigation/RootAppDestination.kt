package com.quial.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import com.plusmobileapps.konnectivity.Konnectivity
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.http.requests.TokenClient
import com.quial.app.paywall.PaywallConfigStateHolder
import com.quial.app.paywall.SubscriptionPaywall
import com.quial.app.screens.auth.AuthUiHelperButtonsAndFirebaseAuth
import com.quial.app.screens.connection.OfflineComposable
import com.quial.app.screens.feed.FeedUiStateHolder
import com.quial.app.screens.feed.comps.FeedScreen
import com.quial.app.screens.feed.quiz.QuizStateHolder
import com.quial.app.screens.onboarding.comps.OnboardingScreen
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.getUiStateHolder
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

interface RootAppDestination {

    companion object {
        private var dataHolder: DataStoreStateHolder? = null
        fun setDataHolder(dataHolder: DataStoreStateHolder) {
            this.dataHolder = dataHolder
        }

        fun getDataHolder(): DataStoreStateHolder {
            return dataHolder!!
        }
    }

    object Onboarding: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.current
            val analytics = Firebase.analytics

            val konnect: Konnectivity = koinInject()
            val connState = remember { konnect }
            val isConnected by connState.isConnectedState.collectAsState()

            if (isConnected) {
                OnboardingScreen(
                    modifier = Modifier,
                    uiStateHolder = getUiStateHolder<OnboardingUiStateHolder>(),
                    dataHolder = getDataHolder(),
                    onNavigateMain = {
                        analytics.logEvent("Onboarding Complete!")
                        navigator?.push(Paywall)
                    },
                    analytics = analytics
                )
            } else {
                OfflineComposable(modifier = Modifier)
            }
        }
    }

    object Auth: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val tokenClient = koinInject<TokenClient>()
            val navigator = LocalNavigator.current

            val konnect: Konnectivity = koinInject()
            val connState = remember { konnect }
            val isConnected by connState.isConnectedState.collectAsState()

            if (isConnected) {
                AuthUiHelperButtonsAndFirebaseAuth(
                    modifier = Modifier,
                    onGoogleSignInResult = { googleUser ->
                        if (googleUser?.idToken?.let { tokenClient.verifyToken(it) } == true)
                            navigator?.push(Feed)
                    },
                    onFirebaseResult = {}
                )
            } else {
                OfflineComposable(modifier = Modifier)
            }
        }
    }

    object Paywall: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.current
            val analytics = Firebase.analytics

            SubscriptionPaywall(
                analytics = analytics,
                navigator = navigator,
                paywallHolder = koinInject<PaywallConfigStateHolder>(),
                onDismiss = {
                    if (navigator?.items?.contains(Auth) == true)
                        navigator.pop() else navigator?.push(Auth)
                },
                listener = object: PaywallListener {
                    override fun onPurchaseCompleted(customerInfo: CustomerInfo?) {
                        super.onPurchaseCompleted(customerInfo)
                        navigator?.push(Auth)
                    }

                    override fun onPurchaseError(error: String?) {
                        super.onPurchaseError(error)
                    }


                    override fun onRestoreCompleted(customerInfo: CustomerInfo?) {
                        super.onRestoreCompleted(customerInfo)
                        navigator?.push(Auth)
                    }

                    override fun onRestoreError(error: String?) {
                        super.onRestoreError(error)
                    }
                }
            )
        }
    }

    object Feed : Screen, RootAppDestination {
        @Composable
        override fun Content() {
            val konnect: Konnectivity = koinInject()
            val connState = remember { konnect }
            val isConnected by connState.isConnectedState.collectAsState()

            if (isConnected) {
                FeedScreen(
                    uiStateHolder = getUiStateHolder<FeedUiStateHolder>(),
                    dataHolder = getDataHolder(),
                    quizHolder = getUiStateHolder<QuizStateHolder>()
                )
            } else {
                OfflineComposable(modifier = Modifier)
            }
        }
    }
}

@Composable
fun RootAppNavigation(startDestination: RootAppDestination) {
    Navigator(screen = startDestination as Screen)
}