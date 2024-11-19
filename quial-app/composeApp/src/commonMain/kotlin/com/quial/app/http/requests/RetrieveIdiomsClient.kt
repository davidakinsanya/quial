package com.quial.app.http.requests

import com.quial.app.data.idiom.Idiom
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import secrets.BuildConfig

class RetrieveIdiomsClient(
    private val httpClient: HttpClient
) {

    suspend fun retrieveIdioms(): List<Idiom>? {
        val response = httpClient.get(urlString = BuildConfig.IDIOM_URL).bodyAsText()
        val serializer = Json {
            ignoreUnknownKeys = true
            isLenient = false
        }
        return serializer.decodeFromString<List<Idiom>?>(response)?.shuffled()
    }
}