package com.quial.app.screens.feed

import com.quial.app.data.idiom.Idiom
import com.quial.app.repository.FeedRepository

data class FeedUiState(
    val feedRepository: FeedRepository
) {
    suspend fun retrieveIdioms(): List<Idiom>? {
        return feedRepository.retrieveIdioms()
    }
}