package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class Statement(
    val enum: String = OnboardingEnums.STATEMENT.name,
    val header: String,
    val text: String): OnboardingSequence
