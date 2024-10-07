package com.quial.app.http.requests

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class TokenClient (
    private val httpClient: HttpClient
) {

    suspend fun verifyToken(token: String): Boolean {
        val response = httpClient.get("https://oauth2.googleapis.com/tokeninfo?id_token=$token").bodyAsText()
        return response.contains("error")
    }
}