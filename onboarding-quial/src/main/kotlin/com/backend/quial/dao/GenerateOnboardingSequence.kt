package com.backend.quial.dao

import com.backend.quial.dto.OnboardingEnums
import com.backend.quial.dto.OnboardingMap
import com.backend.quial.dto.Question
import com.backend.quial.dto.Statement
import com.beust.klaxon.Klaxon
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

class GenerateOnboardingSequence {
    fun generateNewSequence(json: String) {

        val fileCheck = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json").toAbsolutePath().toString()).exists()

        val file = if (!fileCheck) {
            File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json")
                .createFile()
                .toAbsolutePath()
                .toString())

        } else {
            File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json")
                .toAbsolutePath()
                .toString())
        }

        file.printWriter().use { out ->
            out.println(json)
        }
    }

    fun loadSequence(): OnboardingMap {
        val file = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/onboarding.json").toAbsolutePath().toString())

        val onboardingMap: OnboardingMap =
            OnboardingMap(
            questionMap = HashMap(),
            statementMap = HashMap()
            )

        val reader = BufferedReader(file.bufferedReader())
        val json = reader.use { it.readText() }
        val jsonArray = JsonParser.parseString(json).asJsonArray

        for ((count, obj) in jsonArray.withIndex()) {
            when (obj.toString().contains(OnboardingEnums.STATEMENT.name)) {
                true -> {
                    val statement: Statement? = Klaxon().parse(obj.toString())
                    onboardingMap.statementMap[count] = statement!!
                }
                else -> {
                    val question: Question? = Klaxon().parse(obj.toString())
                    onboardingMap.questionMap[count] = question!!
                }
            }
        }

        return onboardingMap
    }
}