package com.quial.app.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(HttpCache) {
            val cacheControl = CacheControl.MaxAge(maxAgeSeconds = 43200, proxyMaxAgeSeconds = 7200)
        }

        install(ContentNegotiation) {
            register(
                ContentType.Application.Json, KotlinxSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10_000 // 10 seconds timeout
            connectTimeoutMillis = 5_000
            socketTimeoutMillis = 5_000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> !response.status.isSuccess() }
            delayMillis { 2000L } // Delay before retry
        }
    }
}