package com.backend.quial.dao.impl

import com.backend.quial.dao.intrface.RetrieveIdioms
import com.backend.quial.dto.Idiom
import com.backend.quial.dto.IdiomList
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

class RetrieveIdiomsImpl: RetrieveIdioms {
  
  /**
   * This method reads a collection of idioms from a csv file.
   *
   * @return a collection of idioms
   */
  override fun readIdioms(): IdiomList {
    val listOfIdioms = mutableListOf<Idiom>()
    val idiomList = IdiomList(listOfIdioms)

    val file = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/quial.csv").toAbsolutePath().toString())
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

    return idiomList
  }
  
  /**
   * This method sorts a collection of idioms according
   * to their alphabetic order.
   *
   * @param listOfIdioms an unsorted collection of idioms
   * @return a sorted collection of idioms.
   */
  override fun sortIdioms(listOfIdioms: List<Idiom>): List<Idiom> {
    TODO("Not yet implemented")
  }
  
}