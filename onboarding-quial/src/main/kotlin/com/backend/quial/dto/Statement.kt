package com.backend.quial.dto

data class Statement(
    val enums: OnboardingEnums = OnboardingEnums.STATEMENT,
    val header: String,
    val text: String): OnboardingSequence
