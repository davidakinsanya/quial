package com.quial.app.http.requests

import com.quial.app.data.stripe.StripeCustomerResponse
import com.quial.app.data.stripe.StripeSubscriptionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import secrets.BuildConfig

class StripeClient(
    private val httpClient: HttpClient,
    private val paywallClient: PaywallClient
) {

    suspend fun getCustomer(email: String): String {
        val customerCall = "customers?email=$email"
        var response = StripeCustomerResponse()
        var key: String

        runBlocking {
            key = paywallClient.retrieveKey()
            response = httpClient.get(BuildConfig.STRIPE_URL + customerCall) {
                header(HttpHeaders.Authorization, key)
            }.body<StripeCustomerResponse>()
        }
        return if (response.data.isEmpty()) ""
        else response.data[0].id
    }

    suspend fun getSubscription(id: String): Boolean {
        val subscriptionCall = "subscriptions?customer=$id"
        var key: String
        var response = StripeSubscriptionResponse()

        runBlocking {
            key = paywallClient.retrieveKey()
            response = httpClient.get(BuildConfig.STRIPE_URL + subscriptionCall) {
                header(HttpHeaders.Authorization, key)
            }.body<StripeSubscriptionResponse>()
        }
        return response.data[0].status == "active"
    }
}