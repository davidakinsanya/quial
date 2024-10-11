package com.quial.app.screens.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import com.quial.app.data.idiom.Idiom
import com.quial.app.repository.FeedRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class
)
data class FeedUiState(
    val feedRepository: FeedRepository
) {
    suspend fun retrieveIdioms(): List<Idiom>? {
        return feedRepository.retrieveIdioms()
    }
}