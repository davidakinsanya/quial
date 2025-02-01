package com.backend.quial.dao

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

class ManageByPassPaywall {

    fun addByPassStatus(bypass: String) {
        val fileCheck = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/bypass-paywall.csv").toAbsolutePath().toString()).exists()
        val jsonArray = JsonParser.parseString(bypass).asJsonArray

        val file = if (!fileCheck) {
            File(
                Paths.get(System.getProperty("user.dir"), "../usr/src/app/bypass-paywall.csv")
                    .createFile()
                    .toAbsolutePath()
                    .toString())

        } else {
            File(
                Paths.get(System.getProperty("user.dir"), "../usr/src/app/bypass-paywall.csv")
                    .toAbsolutePath()
                    .toString())
        }

        file.printWriter().use { out ->
            out.println(jsonArray)
        }
    }
    fun readByPassStatus(): Boolean {
        val file = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/bypass-paywall.csv").toAbsolutePath().toString())
        val reader = BufferedReader(file.bufferedReader())
        val json = reader.use { it.readText() }
        return json.contains("true")
    }
}