package com.quial.app.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import com.quial.app.http.onboarding.dto.OnboardingMap
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
data class OnBoardingScreenData constructor (val map: OnboardingMap)
// TODO: Figure out how to load data into this data class.

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class
)
data class OnboardingScreenUiState constructor(var map: OnboardingMap?, var sequence: List<Any>?)
// TODO: Load This UiState with data