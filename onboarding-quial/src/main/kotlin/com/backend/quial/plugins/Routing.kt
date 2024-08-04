package com.backend.quial.plugins

import com.backend.quial.dao.GenerateOnboardingSequence
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/{json}") {
            call.respond(GenerateOnboardingSequence().generate(call.parameters["json"]!!)!!)
        }
    }
}
