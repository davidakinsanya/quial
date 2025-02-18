package com.backend.quial.dao.impl

import com.backend.quial.dao.intrface.Topics
import com.backend.quial.dto.Idiom
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

class TopicsImpl: Topics {
    override fun pushTopic(topic: String): Boolean {
        val fileCheck = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/topics.txt").toAbsolutePath().toString()).exists()

        println(Paths.get(System.getProperty("user.dir")).toString())

        val file = if (fileCheck) {
            File(Paths.get(System.getProperty("user.dir"),
                "../usr/src/app/topics.txt")
                .toAbsolutePath()
                .toString())
        } else {
            File(Paths.get(System.getProperty("user.dir"),
                "../usr/src/app/topics.txt")
                .createFile()
                .toAbsolutePath()
                .toString())
        }

        file.appendText(topic + "\n")
        return true
    }

    override fun getTopics(): List<String> {
        val list = mutableListOf<String>()
        val file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/topics.txt").toAbsolutePath().toString()).bufferedReader()

        file.forEachLine { list.add(it) }

        return list
    }

    override fun getIdiomsByTopic(topic: String): List<Idiom> {
        val listOfIdioms = mutableListOf<Idiom>()
        val file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/${topic}-quial.csv").toAbsolutePath().toString())

        val regex = """\[(.*?)]""".toRegex()

        val csvReader = csvReader {
            skipEmptyLine = true
        }
        val list = csvReader.readAll(file).toList()

        for (i in 1..<list.size) {
            listOfIdioms.add(
                Idiom(
                    regex.findAll(list[i][0]).map{it.groupValues[1]}.toList(),
                    regex.findAll(list[i][1]).map{it.groupValues[1]}.toList(),
                    regex.findAll(list[i][2]).map{it.groupValues[1]}.toList(),
                )
            )
        }

        return listOfIdioms
    }
}