package com.quial.app.di

import com.quial.app.getHttpClient
import com.quial.app.getHttpConfig
import com.quial.app.http.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val ktorModule = module {
    single <HttpClientEngine> {
        getHttpClient()
    }

    single<HttpClientConfig<*>> {
        getHttpConfig()
    }

    single<HttpClient> {
        createHttpClient(get())
    }
}