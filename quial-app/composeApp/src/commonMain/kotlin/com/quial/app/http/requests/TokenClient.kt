package com.quial.app.http.requests

import com.quial.app.data.auth.GoogleUserInfo
import com.quial.app.data.datastore.DataStoreStateHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking

class TokenClient (
    private val httpClient: HttpClient
) {

    fun verifyToken(token: String,
                    dataHolder: DataStoreStateHolder): Boolean {
        var response = ""
        runBlocking {
            val responseObj = httpClient.get("https://oauth2.googleapis.com/tokeninfo?id_token=$token")
                .body<GoogleUserInfo>()

            dataHolder.setName(responseObj.given_name)
            dataHolder.setImage(responseObj.picture)

            response = responseObj.toString()
        }
        return !response.contains("error")
    }

    fun getResponseEmail(token: String): String {
        var response = GoogleUserInfo()

        runBlocking {
            response = httpClient.get("https://oauth2.googleapis.com/tokeninfo?id_token=$token")
                .body<GoogleUserInfo>()

        }

        return response.email
    }
}