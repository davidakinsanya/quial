package com.quial.app

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun onApplicationStartPlatformSpecific()

expect fun getHttpClient(): HttpClientEngine

expect fun getHttpConfig(): HttpClientConfig<*>