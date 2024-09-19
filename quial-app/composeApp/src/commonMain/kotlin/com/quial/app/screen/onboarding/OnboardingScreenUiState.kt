package com.quial.app.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.repository.OnboardingRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class
)
data class OnboardingScreenUiState constructor(val repository: OnboardingRepository)
// TODO: Load This UiState with data