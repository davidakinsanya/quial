package com.quial.app.data.stripe

import kotlinx.serialization.Serializable

@Serializable
data class StripeCustomerResponse(
    var obj: String,
    var data: List<Customer> = listOf()
)

@Serializable
data class Customer(
    var id: String,
    var email: String? = null,
    var name: String? = null
)

@Serializable
data class StripeSubscriptionResponse(
    var obj: String,
    var data: List<Subscription>
)

@Serializable
data class Subscription(
    var id: String,
    var status: String,
    var items: SubscriptionItems
)

@Serializable
data class SubscriptionItems(
    var data: List<SubscriptionItem>
)

@Serializable
data class SubscriptionItem(
    var price: Price
)

@Serializable
data class Price(
    var product: String
)