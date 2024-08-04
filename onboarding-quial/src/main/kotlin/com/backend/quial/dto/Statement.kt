package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class Statement(
    val enum: OnboardingEnums = OnboardingEnums.STATEMENT,
    val header: String,
    val text: String): OnboardingSequence
