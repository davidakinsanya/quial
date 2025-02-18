package com.backend.quial.dao.impl

import com.backend.quial.dao.intrface.Topics
import com.backend.quial.dto.Idiom
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import kotlin.io.path.createFile

class TopicsImpl: Topics {
    override fun pushTopic(topic: String): Boolean {
        var file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/topics/topics.txt").toAbsolutePath().toString())
        if (file.exists()) {
            file.appendText("\n$topic")
        } else {
            file = File(Paths.get(System.getProperty("user.dir"),
                "../usr/src/app/topics/topics.txt")
                .createFile()
                .toAbsolutePath()
                .toString())

            file.appendText(topic)
        }

        return true
    }

    override fun getTopics(): List<String> {
        val list = mutableListOf<String>()
        val file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/topics/topics.txt").toAbsolutePath().toString()).bufferedReader()

        file.forEachLine { list.add(it) }

        return list
    }

    override fun getIdiomsByTopic(topic: String): List<Idiom> {
        val listOfIdioms = mutableListOf<Idiom>()
        val file = File(Paths.get(System.getProperty("user.dir"),
            "../usr/src/app/topics/${topic}-quial.csv").toAbsolutePath().toString())

        val fileInputStream = FileInputStream(file)
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