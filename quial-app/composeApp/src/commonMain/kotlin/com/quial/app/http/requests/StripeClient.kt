package com.quial.app.http.requests

import com.quial.app.data.stripe.StripeCustomerResponse
import com.quial.app.data.stripe.StripeSubscriptionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import secrets.BuildConfig

class StripeClient(
    private val httpClient: HttpClient,
    private val paywallClient: PaywallClient
) {

    suspend fun getCustomer(email: String): String {
        val customerCall = "customers?email=$email"
        val key = paywallClient.retrieveKey().trim()

        val response = httpClient.get(BuildConfig.STRIPE_URL + customerCall) {
            header(HttpHeaders.Authorization, "Bearer $key")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }.body<StripeCustomerResponse>()

        return if (response.data.isEmpty()) ""
        else response.data[0].id
    }

    suspend fun getSubscription(id: String): Boolean {
        var bool = false
        val subscriptionCall = "subscriptions?customer=$id"

        val key = paywallClient.retrieveKey().trim()
        val response = httpClient.get(BuildConfig.STRIPE_URL + subscriptionCall) {
            header(HttpHeaders.Authorization, "Bearer $key")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }.body<StripeSubscriptionResponse>()


        response.data.forEach {  bool = it.status == "active" }
        return if (response.data.isEmpty()) false
        else bool
    }
}