package com.quial.app.repository

import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.http.requests.OnboardingClient
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.launch

class OnboardingRepository(
    private val onboardingClient: OnboardingClient,
    private val stateHolder: OnboardingUiStateHolder
) {

    suspend fun getOnboardingMap(): OnboardingMap? {
        var map: OnboardingMap? = null
        stateHolder.uiStateHolderScope.launch {
           map = onboardingClient.getOnboardingSequence()
        }
        return map
    }
}