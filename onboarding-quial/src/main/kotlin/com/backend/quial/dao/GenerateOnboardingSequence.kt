package com.backend.quial.dao

import com.backend.quial.dto.OnboardingEnums
import com.backend.quial.dto.OnboardingSequence
import com.backend.quial.dto.Question
import com.backend.quial.dto.Statement
import com.beust.klaxon.Klaxon
import java.io.File

class GenerateOnboardingSequence {
    fun generateNewSequence(json: String) {
        val file = File("../usr/src/json.text")
        file.writeText(json)
    }

    fun loadSequence(): List<OnboardingSequence>? {
        return Klaxon().parse<List<OnboardingSequence>>(File("../usr/src/app/json.txt"))
    }
}