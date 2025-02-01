package com.backend.quial.plugins

import com.backend.quial.dao.ManageByPassPaywall
import com.backend.quial.dao.ManagePaywallConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            val paywallConfig = ManagePaywallConfig()
            call.respond(paywallConfig.readConfig())
        }

        post("/generate") {
            val onboarding = call.parameters["onboarding"]
            val feed = call.parameters["feed"]
            val paywallConfig = ManagePaywallConfig()

            call.respond(paywallConfig.addNewConfig(onboarding!!, feed!!))
        }

        get("/retrieveBypass") {
            val manage = ManageByPassPaywall()
            call.respond(manage.readByPassStatus())
        }

        post("/generateBypass") {
            val bypass: String? = call.parameters["bypass"]
            val manage = ManageByPassPaywall()

            call.respond(manage.addByPassStatus(bypass!!))
        }
    }
}
