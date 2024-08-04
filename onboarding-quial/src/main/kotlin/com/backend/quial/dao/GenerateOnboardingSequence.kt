package com.backend.quial.dao

import com.backend.quial.dto.OnboardingEnums
import com.backend.quial.dto.OnboardingSequence
import com.backend.quial.dto.Question
import com.backend.quial.dto.Statement
import com.beust.klaxon.Klaxon

class GenerateOnboardingSequence {
    fun generate(json: String): List<OnboardingSequence>? {
        return Klaxon().parse<List<OnboardingSequence>>(json)
    }
}