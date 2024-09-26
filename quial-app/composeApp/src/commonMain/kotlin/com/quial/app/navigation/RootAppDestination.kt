package com.quial.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.quial.app.screen.onboarding.OnboardingScreen
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.getUiStateHolder
import org.koin.compose.koinInject

interface RootAppDestination {

    object Onboarding: Screen, RootAppDestination {

        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val onBoardingUiStateHolder = getUiStateHolder<OnboardingUiStateHolder>()
            OnboardingScreen(
                modifier = Modifier,
                uiStateHolder = onBoardingUiStateHolder,
                onNavigateMain = {}
            )
        }
    }
}

@Composable
fun RootAppNavigation(startDestination: RootAppDestination) {
    Navigator(screen = startDestination as Screen)
}