package com.quial.app

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun onApplicationStartPlatformSpecific()

expect fun getHttpClient(): HttpClientEngine

expect fun getHttpConfig(): HttpClientConfig<*>

expect fun sharedPlatformModule(): Module

expect class Growth {
    fun inAppRating()
}