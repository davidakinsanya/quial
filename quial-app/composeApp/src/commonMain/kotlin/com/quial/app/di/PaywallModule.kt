package com.quial.app.di

import com.quial.app.http.requests.PaywallClient
import com.quial.app.http.requests.StripeClient
import com.quial.app.paywall.PaywallConfigState
import com.quial.app.paywall.PaywallConfigStateHolder
import org.koin.dsl.module

val paywallModule = module {
    single <StripeClient> {
        StripeClient(get(), get())
    }
    single<PaywallClient> {
        PaywallClient(get())
    }

    single<PaywallConfigState> {
        PaywallConfigState(get())
    }

    single<PaywallConfigStateHolder> {
        PaywallConfigStateHolder(get())
    }
}