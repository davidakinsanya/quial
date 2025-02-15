package com.quial.app.http.requests

import io.ktor.client.HttpClient

class StripeClient(
    private val httpClient: HttpClient
) {

    fun getCustomer(email: String): String {
        val customerCall = "customers?email=$email"
        return ""
    }

    fun getSubscription(id: String): Boolean {
        val subscriptionCall = "subscriptions?customer=$id"
        return false
    }
}