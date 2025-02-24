package com.quial.app.screens.feed.comps

import com.quial.app.data.idiom.Idiom

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<Idiom>) : UiState() // Replace with your data type
    data class Error(val message: String) : UiState()
}