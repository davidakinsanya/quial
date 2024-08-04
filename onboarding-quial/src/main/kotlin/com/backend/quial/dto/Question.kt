package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class Question(val enum: OnboardingEnums = OnboardingEnums.QUESTION,
                    val question: String,
                    val options: List<String>): OnboardingSequence
