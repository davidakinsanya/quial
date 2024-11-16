package com.quial.app.paywall

import kotlinx.serialization.Serializable

@Serializable
data class PaywallConfig(val map: Map<String, String>)
