package com.backend.quial.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaywallConfig(val map: Map<String, String>)
