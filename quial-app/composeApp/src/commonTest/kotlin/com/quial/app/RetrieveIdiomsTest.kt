package com.quial.app

import assertk.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test

class RetrieveIdiomsTest {

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
    }

    private val url = "http://localhost:8081/"

    @Test
    fun retrieveIdioms() = runTest {
        val res = httpClient.get(urlString = url)
        assertThat(res.status.value >= 200)
    }
}