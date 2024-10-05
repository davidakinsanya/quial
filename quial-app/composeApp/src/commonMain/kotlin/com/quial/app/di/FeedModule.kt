package com.quial.app.di

import com.quial.app.http.requests.RetrieveIdiomsClient
import com.quial.app.repository.FeedRepository
import com.quial.app.screen.feed.FeedUiState
import com.quial.app.screen.feed.FeedUiStateHolder
import org.koin.dsl.module

val feedModule = module {
    single<RetrieveIdiomsClient> {
        RetrieveIdiomsClient(get())
    }

    single<FeedRepository> {
        FeedRepository(get())
    }

    single<FeedUiState> {
        FeedUiState(get())
    }

    single<FeedUiStateHolder> {
        FeedUiStateHolder(get())
    }
}