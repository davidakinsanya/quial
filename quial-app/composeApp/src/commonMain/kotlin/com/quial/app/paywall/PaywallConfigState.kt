package com.quial.app.paywall

import com.quial.app.http.requests.PaywallClient

class PaywallConfigState(
    private val client: PaywallClient
) {

    suspend fun readConfig(): PaywallConfig {
        return client.readConfig()
    }
}