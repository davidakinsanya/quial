package com.backend.quial.dao

import com.backend.quial.dto.PaywallConfig
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

class ManagePaywallConfig {

    fun readConfig(): PaywallConfig {
        val map = mutableMapOf(
            Pair("onboarding", "false"),
            Pair("feed", "false")
        )

        val config = PaywallConfig(map)

        val file = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/paywall.csv").toAbsolutePath().toString())

        val csvReader = csvReader {
            skipEmptyLine = true
        }

        val list = csvReader.readAll(file).toList()

        map["onboarding"] = list[1][0]
        map["feed"] = list[1][1]

       return config
    }

    fun addNewConfig(onboardingConfig: String, feedConfig: String) {

        val fileCheck = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/paywall.csv").toAbsolutePath().toString()).exists()

        val file = if (!fileCheck) {
            File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/paywall.csv")
                .createFile()
                .toAbsolutePath()
                .toString())

        } else {
            File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/paywall.csv")
                .toAbsolutePath()
                .toString())
        }


        val map = mapOf(
            Pair("onboarding", onboardingConfig),
            Pair("feed", feedConfig)
        )
        var csv = ""
        csv += "onboarding, feed\n"
        map.forEach { value ->
            csv += "$value, "
        }


        file.printWriter().use { out ->
            out.println(csv.dropLast(2))
        }
    }

}