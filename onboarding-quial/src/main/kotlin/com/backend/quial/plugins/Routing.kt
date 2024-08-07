package com.backend.quial.plugins

import com.backend.quial.dao.GenerateOnboardingSequence
import com.backend.quial.dto.OnboardingSequence
import com.beust.klaxon.Klaxon
import com.google.gson.JsonParser
import com.google.gson.JsonObject
import com.google.gson.Gson
import com.google.gson.JsonArray
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.regex.Pattern

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
