package com.backend.quial.dto

data class Question(val enums: OnboardingEnums = OnboardingEnums.QUESTION,
                    val question: String,
                    val options: List<String>): OnboardingSequence
