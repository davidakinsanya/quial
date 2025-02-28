package com.quial.app.http.requests

import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.data.onboarding.Question
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
        return try {
            serializer.decodeFromString(response)
        } catch(e: Exception) {
            OnboardingMap(questionMap = hashMapOf(
                0 to
                Question(
                    question = "Are you ready to master idioms with quial?",
                    options = listOf("Yes, I am always ready to learn")
                )),
                statementMap = HashMap())
        }
    }
}