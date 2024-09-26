package com.quial.app.repository

import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.http.requests.OnboardingClient
import kotlinx.coroutines.runBlocking

class OnboardingRepository(
    private val onboardingClient: OnboardingClient
) {

    suspend fun getOnboardingMap(): OnboardingMap? {
        var map: OnboardingMap? = null
        runBlocking {
           map = onboardingClient.getOnboardingSequence()
        }
        return map
    }
}