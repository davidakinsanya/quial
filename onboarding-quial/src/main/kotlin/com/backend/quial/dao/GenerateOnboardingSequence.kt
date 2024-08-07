package com.backend.quial.dao

import com.backend.quial.dto.OnboardingEnums
import com.backend.quial.dto.OnboardingSequence
import com.backend.quial.dto.Question
import com.backend.quial.dto.Statement
import com.beust.klaxon.Klaxon
import java.io.File
import java.nio.file.Paths

class GenerateOnboardingSequence {
    fun generateNewSequence(json: String) {
        val file =  File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json").toAbsolutePath().toString())
        file.printWriter().use { out ->
            out.println(json)
        }
    }

    fun loadSequence(): List<Any> {
        val file = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json").toAbsolutePath().toString())
        val list = mutableListOf<Any>()
        val json = Klaxon().toJsonObject(file) // TODO: Make changes in the JSON parsing!

        for (obj in json) {
            when (obj.key) {
                OnboardingEnums.STATEMENT.name -> {
                    val statement: Statement? = Klaxon().parse(obj.toString())
                    list.add(statement!!)
                }
                else -> {
                    val question: Question? = Klaxon().parse(obj.toString())
                    list.add(question!!)
                }
            }
        }

        return list
    }
}