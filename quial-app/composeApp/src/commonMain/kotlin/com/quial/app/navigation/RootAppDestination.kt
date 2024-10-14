package com.quial.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.http.requests.TokenClient
import com.quial.app.paywall.SubscriptionPaywall
import com.quial.app.screens.auth.AuthUiHelperButtonsAndFirebaseAuth
import com.quial.app.screens.feed.FeedUiStateHolder
import com.quial.app.screens.feed.comps.FeedScreen
import com.quial.app.screens.feed.quiz.QuizStateHolder
import com.quial.app.screens.onboarding.comps.OnboardingScreen
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.getUiStateHolder
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

            OnboardingScreen(
                modifier = Modifier,
                uiStateHolder = getUiStateHolder<OnboardingUiStateHolder>(),
                dataHolder = getDataHolder(),
                onNavigateMain = { navigator?.push(Paywall) }
            )
        }
    }

    object Auth: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val tokenClient = koinInject<TokenClient>()
            val navigator = LocalNavigator.current

            AuthUiHelperButtonsAndFirebaseAuth(
                modifier = Modifier,
                onGoogleSignInResult = { googleUser ->
                    if (googleUser?.idToken?.let { tokenClient.verifyToken(it) } == true)
                        navigator?.push(Feed)
                },
                onFirebaseResult = {}
            )
        }
    }

    object Paywall: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.current

            SubscriptionPaywall(
                onDismiss = { navigator?.push(Auth) },
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
            FeedScreen(
                uiStateHolder = getUiStateHolder<FeedUiStateHolder>(),
                dataHolder = getDataHolder(),
                quizHolder = getUiStateHolder<QuizStateHolder>()
            )
        }
    }
}

@Composable
fun RootAppNavigation(startDestination: RootAppDestination) {
    Navigator(screen = startDestination as Screen)
}