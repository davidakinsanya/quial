package com.backend.quial.plugins

import com.backend.quial.dao.RetrieveKey
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/key") {
            call.respond(RetrieveKey().retrieve())
        }
    }
}
