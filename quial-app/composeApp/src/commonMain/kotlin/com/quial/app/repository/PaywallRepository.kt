package com.quial.app.repository

import com.mmk.kmprevenuecat.purchases.Purchases
import kotlinx.coroutines.runBlocking

class PaywallRepository () {
    fun getPremium(): Boolean {
        var isPremium = false
        runBlocking {
            Purchases.getCustomerInfo { customerInfo ->
                customerInfo.getOrNull()?.entitlements?.all?.values?.iterator()?.forEach {
                    if (it.isActive) {
                        isPremium = true
                    }
                }
            }
        }
        return isPremium
    }
}