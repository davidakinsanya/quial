package com.backend.quial.plugins

import com.backend.quial.dao.impl.AddUserIdiomImpl
import com.backend.quial.dao.impl.RetrieveIdiomsImpl
import com.backend.quial.dao.impl.TopicsImpl
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

        post("/add-topic") {
            val topic: String? = call.parameters["topic"]
            val topicImpl = TopicsImpl()
            val bool = topicImpl.pushTopic(topic!!)
            call.respond(bool)

        }

        get("/get-topics-list") {
            val topicImpl = TopicsImpl()
            val topics = topicImpl.getTopics()
            call.respond(topics)
        }

        get ("/get-idioms-by-topic") {
            val topic: String? = call.parameters["topic"]!!
            val topicsImpl = TopicsImpl()
            val idioms = topicsImpl.getIdiomsByTopic(topic!!)
            call.respond(idioms)
        }

    }
}
