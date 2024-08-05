package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class Question(val enum: String = OnboardingEnums.QUESTION.name,
                    val question: String,
                    val options: List<String>): OnboardingSequence
