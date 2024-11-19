package com.quial.app.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.http.CacheControl

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(HttpCache) {
            val cacheControl = CacheControl.MaxAge(maxAgeSeconds = 7200, proxyMaxAgeSeconds = 7200)
        }
    }
}