package com.quial.app.di

import com.quial.app.http.requests.OnboardingClient
import com.quial.app.repository.OnboardingRepository
import com.quial.app.screens.onboarding.OnboardingScreenUiState
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import org.koin.dsl.module

val onboardingModule = module {

    single<OnboardingClient> {
        OnboardingClient(get())
    }

    single<OnboardingRepository> {
        OnboardingRepository(get())
    }

    single<OnboardingScreenUiState> {
        OnboardingScreenUiState(get())
    }

    single<OnboardingUiStateHolder> {
        OnboardingUiStateHolder(get())
    }
}