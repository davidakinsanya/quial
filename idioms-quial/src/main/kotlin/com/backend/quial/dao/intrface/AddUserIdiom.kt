package com.backend.quial.dao.intrface

/**
 * This interface holds the classes responsible for adding user generated idioms.
 *
 * @author David.
 */
interface AddUserIdiom {

    /**
     * This function adds new idioms added to the quial platform by users.
     *
     * @param idiom a new idiom suggested by a user.
     *
     * @return an integer representing whether the new idiom passes safety checks.
     */
    fun add(idiom: String): Int
}