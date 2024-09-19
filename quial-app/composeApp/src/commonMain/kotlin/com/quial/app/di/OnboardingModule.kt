package com.quial.app.di

import com.quial.app.http.createHttpClient
import com.quial.app.http.requests.OnboardingClient
import com.quial.app.repository.OnboardingRepository
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import io.ktor.client.HttpClient
import org.koin.dsl.module

val onboardingModule = module {

    single<HttpClient> {
        createHttpClient(get())
    }

    single {
        OnboardingClient(get())
    }

    single {
        OnboardingRepository(get(), get())
    }

    single {
        OnboardingUiStateHolder(get())
    }

}