package com.backend.quial.dao.intrface

import com.backend.quial.dto.Idiom

interface RetrieveIdioms {
  /**
   * This method reads a collection of idioms from a csv file.
   *
   * @return a collection of idioms
   */
  fun readIdioms(): List<Idiom>
  
  /**
   * This method sorts a collection of idioms according
   * to their alphabetic order.
   *
   * @param listOfIdioms an unsorted collection of idioms
   * @return a sorted collection of idioms.
   */
  fun sortIdioms(listOfIdioms: List<Idiom>): List<Idiom>
}