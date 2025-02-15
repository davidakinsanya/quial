package com.backend.quial.dao

import java.io.BufferedReader
import java.io.File
import java.nio.file.Paths

class RetrieveKey {

    fun retrieve(): String {
        val file = File(Paths.get(System.getProperty("user.dir"),
            "/src/main/secrets/.txt")
            .toAbsolutePath()
            .toString())

        val reader = BufferedReader(file.bufferedReader())
        return reader.use { it.readText() }
    }
}