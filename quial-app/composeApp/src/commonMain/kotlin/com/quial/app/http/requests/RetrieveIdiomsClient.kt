package com.quial.app.http.requests

import com.quial.app.data.idiom.Idiom
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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

    suspend fun retrieveTopics(): List<String> {
        val response: List<String> = httpClient.get(urlString = BuildConfig.IDIOM_URL + "/get-topics-list").body()
        return response
    }

    suspend fun getTopicsByIdiom(topic: String): List<Idiom>? {
        val response = httpClient.get(urlString = BuildConfig.IDIOM_URL + "/get-idioms-by-topic?topic=$topic").bodyAsText()

        val serializer = Json {
            ignoreUnknownKeys = true
            isLenient = false
        }

        return serializer.decodeFromString<List<Idiom>?>(response)?.shuffled()
    }
}