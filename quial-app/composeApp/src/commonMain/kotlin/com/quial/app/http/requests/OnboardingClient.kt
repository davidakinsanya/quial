package com.quial.app.http.requests

import com.quial.app.data.onboarding.OnboardingMap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import secrets.BuildConfig

class OnboardingClient(
    private val httpClient: HttpClient
) {

    suspend fun getOnboardingSequence(): OnboardingMap? {
        val response = httpClient.get(urlString = BuildConfig.ONBOARDING_URL)

        val body: OnboardingMap? = when (response.status.value) {
            in 200..299 -> {
                response.body<OnboardingMap>()
            }

            else -> null
        }
        return body
    }
}

// TODO: Write up a repository to connect this code to the data class OnboardingScreenUiState