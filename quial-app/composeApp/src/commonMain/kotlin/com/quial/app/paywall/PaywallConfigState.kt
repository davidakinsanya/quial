package com.quial.app.paywall

import com.quial.app.http.requests.PaywallConfigClient

class PaywallConfigState(
    private val client: PaywallConfigClient
) {

    suspend fun readConfig(): PaywallConfig {
        return client.readConfig()
    }
}