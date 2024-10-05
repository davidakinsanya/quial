package com.quial.app.repository

import com.quial.app.data.idiom.Idiom
import com.quial.app.http.requests.RetrieveIdiomsClient
import kotlinx.coroutines.runBlocking

class FeedRepository(
    private val idiomsClient: RetrieveIdiomsClient
) {

    suspend fun retrieveIdioms(): List<Idiom>? {
        var idiomList: List<Idiom>? = null

        runBlocking {
            idiomList = idiomsClient.retrieveIdioms()
        }

        return idiomList
    }
}