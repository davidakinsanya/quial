package com.quial.app.di

import com.quial.app.getHttpClient
import com.quial.app.getHttpConfig
import com.quial.app.http.createHttpClient
import com.quial.app.http.requests.OnboardingClient
import com.quial.app.repository.OnboardingRepository
import com.quial.app.screen.onboarding.OnboardingScreenUiState
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val onboardingModule = module {

    single <HttpClientEngine> {
        getHttpClient()
    }

    single<HttpClientConfig<*>> {
        getHttpConfig()
    }

    single<HttpClient> {
        createHttpClient(get())
    }

    single {
        OnboardingClient(get())
    }

    single {
        OnboardingScreenUiState(get())
    }

    single {
        OnboardingRepository(get(), get())
    }

    single {
        OnboardingUiStateHolder(get())
    }

}