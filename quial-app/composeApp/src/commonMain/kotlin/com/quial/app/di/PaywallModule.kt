package com.quial.app.di

import com.quial.app.http.requests.PaywallConfigClient
import com.quial.app.paywall.PaywallConfigState
import com.quial.app.paywall.PaywallConfigStateHolder
import org.koin.dsl.module

val paywallModule = module {
    single<PaywallConfigClient> {
        PaywallConfigClient(get())
    }

    single<PaywallConfigState> {
        PaywallConfigState(get())
    }

    single<PaywallConfigStateHolder> {
        PaywallConfigStateHolder(get())
    }
}