package com.backend.quial

import com.backend.quial.plugins.configureRouting
import com.backend.quial.plugins.configureSecurity
import com.backend.quial.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    configureSecurity()
    configureSerialization()
}
