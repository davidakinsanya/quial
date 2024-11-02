package com.backend.quial.plugins

import com.backend.quial.dao.impl.AddUserIdiomImpl
import com.backend.quial.dao.impl.RetrieveIdiomsImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.nio.file.Files
import java.nio.file.Paths

fun Application.configureRouting() {
    routing {
        get("/") {
            val retrieveIdiomsImpl = RetrieveIdiomsImpl()
            val list = retrieveIdiomsImpl.readIdioms().shuffled()
            call.respond(list)
        }

        get("/count") {
            val retrieveIdiomsImpl = RetrieveIdiomsImpl()
            call.respond(retrieveIdiomsImpl.readIdioms().size)
        }

        get("/add") {
            val idiom = call.parameters["idiom"]!!
            call.respond(AddUserIdiomImpl().add(idiom))

        }

        get("paths") {
            call.respondText(Files.exists(Paths.get(System.getProperty("user.dir"), "../usr/src/app").toAbsolutePath()).toString())
        }
    }
}
