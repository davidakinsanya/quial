package com.quial.app.di

import com.quial.app.http.requests.TokenClient
import org.koin.dsl.module

val tokenModule = module {
    single<TokenClient> {
        TokenClient(get())
    }
}