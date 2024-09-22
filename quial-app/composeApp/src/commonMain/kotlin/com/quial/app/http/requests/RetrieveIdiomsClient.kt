package com.quial.app.http.requests

import com.quial.app.data.idiom.Idiom
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.call.body
import secrets.BuildConfig

class RetrieveIdiomsRequest(
    private val httpClient: HttpClient
) {

    suspend fun retrieveIdioms(): List<Idiom>? {
        val response = httpClient.get(urlString = BuildConfig.IDIOM_URL)

        val list: List<Idiom>? = when (response.status.value) {
            in 200 ..299 -> {
                response.body<List<Idiom>>()
            }

            else -> null
        }
        return list
    }
}