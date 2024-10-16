package com.quial.app

import com.quial.app.data.onboarding.OnboardingMap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import secrets.BuildConfig
import kotlin.test.Test
import kotlin.test.assertTrue

class OnboardingTest {

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val url = BuildConfig.ONBOARDING_URL

    @Test
    fun getOnboardingMap() = runTest {
        val res = httpClient.get(url)

        println(res.body<OnboardingMap>().toString())

        assertTrue { res.status.value in 299 downTo 200 }
        assertTrue { res.body<OnboardingMap>().statementMap.isNotEmpty() }
    }
}