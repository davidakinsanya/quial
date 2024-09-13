package com.backend.quial.dao.impl

import com.backend.quial.dao.intrface.AddUserIdiom
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createFile

/**
 * This class holds the implementation of the classes responsible for adding user generated idioms.
 *
 * @author David.
 */
class AddUserIdiomImpl : AddUserIdiom {

    /**
     * This function adds new idioms added to the quial platform by users.
     *
     * @param idiom a new idiom suggested by a user.
     *
     * @return an integer representing whether the new idiom passes safety checks.
     */
    override fun add(idiom: String): Int {

        var userFile = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/user-quial.csv").toAbsolutePath().toString())

        if (!userFile.exists()) {
            userFile = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/user-quial.csv").toAbsolutePath().createFile().toString())
        }

        userFile.printWriter().use { out ->
            out.println(idiom)
        }

        val mainQuialFile = File(Paths.get(System.getProperty("user.dir"), "../usr/src/app/quial.csv").toAbsolutePath().toString())
        mainQuialFile.printWriter().use {
                out -> out.println(idiom)
        }

        // TODO: ADD IDIOM SAFETY CHECK AND RETURN -1 IF THE CHECK FAILS !!
        return 1

    }

}