package com.backend.quial.dao

import com.backend.quial.dto.OnboardingEnums
import com.backend.quial.dto.Question
import com.backend.quial.dto.Statement
import com.beust.klaxon.Klaxon
import com.google.gson.JsonArray
import com.google.gson.JsonParser
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
        val jsonArray: JsonArray = JsonParser.parseString(file.toString()).asJsonArray

        for (obj in jsonArray) {
            when (obj.toString().contains(OnboardingEnums.STATEMENT.name)) {
                true -> {
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