package com.quial.app.http.requests

import com.quial.app.data.onboarding.OnboardingMap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import secrets.BuildConfig

class OnboardingClient(
    private val httpClient: HttpClient
) {

    suspend fun getOnboardingSequence(): OnboardingMap? {
        val response = httpClient.get(urlString = BuildConfig.ONBOARDING_URL).bodyAsText()
        val serializer = Json {
            ignoreUnknownKeys = true
            isLenient = false
        }
        return serializer.decodeFromString(response)
    }
}