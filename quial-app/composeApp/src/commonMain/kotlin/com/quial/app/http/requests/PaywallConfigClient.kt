package com.quial.app.http.requests

import com.quial.app.paywall.PaywallConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import secrets.BuildConfig

class PaywallConfigClient(
    private val httpClient: HttpClient
) {
    suspend fun readConfig(): PaywallConfig {
        val response = httpClient.get(urlString = BuildConfig.PAYWALL_URL).bodyAsText()
        val serializer = Json {
            ignoreUnknownKeys = true
            isLenient = false
        }

        return serializer.decodeFromString(response)
    }
}