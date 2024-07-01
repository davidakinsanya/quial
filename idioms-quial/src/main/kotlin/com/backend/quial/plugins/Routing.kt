package com.backend.quial.plugins

import com.backend.quial.dao.impl.RetrieveIdiomsImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            val retrieveIdiomsImpl = RetrieveIdiomsImpl()
            call.respond(retrieveIdiomsImpl.sortIdioms(retrieveIdiomsImpl.readIdioms()))
        }
    }
}
