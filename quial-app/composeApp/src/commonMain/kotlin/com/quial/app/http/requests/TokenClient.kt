package com.quial.app.http.requests

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking

class TokenClient (
    private val httpClient: HttpClient
) {

    fun verifyToken(token: String): Boolean {
        var response = ""
        runBlocking {
            response = httpClient.get("https://oauth2.googleapis.com/tokeninfo?id_token=$token")
                .bodyAsText()

        }
        return !response.contains("error")
    }
}