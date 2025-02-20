package com.quial.app.screens.feed

import com.quial.app.data.idiom.Idiom
import com.quial.app.repository.FeedRepository

data class FeedUiState(
    val feedRepository: FeedRepository
) {
    suspend fun retrieveIdioms(): List<Idiom>? {
        return feedRepository.retrieveIdioms()
    }

    suspend fun retrieveTopics(): List<String> {
        return feedRepository.retrieveTopics()
    }

    suspend fun getIdiomsByTopic(topic: String): List<Idiom>? {
        return feedRepository.getIdiomsByTopic(topic)
    }
}