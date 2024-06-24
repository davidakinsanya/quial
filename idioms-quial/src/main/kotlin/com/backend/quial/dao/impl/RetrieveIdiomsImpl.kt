package com.backend.quial.dao.impl

import com.backend.quial.dao.intrface.RetrieveIdioms
import com.backend.quial.dto.Idiom
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import java.io.FileInputStream

class RetrieveIdiomsImpl: RetrieveIdioms {
  
  /**
   * This method reads a collection of idioms from a csv file.
   *
   * @return a collection of idioms
   */
  override fun readIdioms(): List<Idiom> {
    val listOfIdioms = mutableListOf<Idiom>()
    
    val file = File("../../python/quial.csv")
    val fileInputStream = FileInputStream(file)
    val regex = Regex("\\[(.*?)]")
    
    
    csvReader().open(fileInputStream) {
      readAllWithHeaderAsSequence().map {
        listOfIdioms.add(
         Idiom(
           regex.findAll(it["basic-info"]!!).map{it.groupValues[1]}.toList(),
           regex.findAll(it["meaning"]!!).map{it.groupValues[1]}.toList(),
           regex.findAll(it["example-sentences"]!!).map{it.groupValues[1]}.toList()
        )
       )
      }
    }
    
    
    return listOfIdioms
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