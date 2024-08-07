package com.backend.quial.plugins

import com.backend.quial.dao.GenerateOnboardingSequence
import com.backend.quial.dto.OnboardingSequence
import com.beust.klaxon.Klaxon
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(GenerateOnboardingSequence().loadSequence())
        }

        post("/generate") {
            val json = call.receive<String>()
            call.respond(GenerateOnboardingSequence().generateNewSequence(json))
        }
    }
}
