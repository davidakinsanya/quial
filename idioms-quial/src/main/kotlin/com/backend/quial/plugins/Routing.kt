package com.backend.quial.plugins

import com.backend.quial.dao.impl.AddUserIdiomImpl
import com.backend.quial.dao.impl.RetrieveIdiomsImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

fun Application.configureRouting() {
    routing {
        get("/") {
            val retrieveIdiomsImpl = RetrieveIdiomsImpl()
            call.respond(retrieveIdiomsImpl.readIdioms())
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
