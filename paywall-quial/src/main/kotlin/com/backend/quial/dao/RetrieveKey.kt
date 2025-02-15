package com.backend.quial.dao

import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

class RetrieveKey {

    fun retrieve(): String {
        val file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/api-key.txt")
            .toAbsolutePath()
            .toString())

        val reader = BufferedReader(file.bufferedReader())
        return reader.use { it.readText() }
    }

    fun pushKey(key: String) {
        val fileCheck = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/api-key.txt").toAbsolutePath().toString()).exists()


        val file = if (!fileCheck) {
            File(
                Paths.get(System.getProperty("user.dir"),
                    "../usr/src/app/api-key.txt")
                    .createFile()
                    .toAbsolutePath()
                    .toString())

        } else {
            File(
                Paths.get(System.getProperty("user.dir"),
                    "../usr/src/app/api-key.txt")
                    .toAbsolutePath()
                    .toString())
        }

        file.printWriter().use { out ->
            out.println(key)
        }
    }
}