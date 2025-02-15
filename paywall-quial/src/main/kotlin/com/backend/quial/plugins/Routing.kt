package com.backend.quial.plugins

import com.backend.quial.dao.RetrieveKey
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/key") {
            val retrieveKey = RetrieveKey()
            call.respond(retrieveKey.retrieve())
        }

        post ("/push") {
            val key: String? = call.parameters["key"]
            val retrieveKey = RetrieveKey()
            call.respond(retrieveKey.pushKey(key!!))
        }
    }
}
