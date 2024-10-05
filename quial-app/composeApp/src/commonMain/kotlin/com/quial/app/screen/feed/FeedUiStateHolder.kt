package com.quial.app.screen.feed

import com.quial.app.data.idiom.Idiom
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedUiStateHolder(
    private val feedUiState: FeedUiState
): UiStateHolder() {
    private val _uiState = MutableStateFlow(feedUiState)
    private val uiState = _uiState.asStateFlow()

    private val _idiomsList: MutableStateFlow<List<Idiom>> = MutableStateFlow(listOf())
    val idiomsList = _idiomsList
        .onStart { loadData() }
        .stateIn(
        uiStateHolderScope,
        SharingStarted.WhileSubscribed(5000L),
        listOf()
    )

    private fun loadData() = uiStateHolderScope.launch {
        _idiomsList.value = _uiState.value.retrieveIdioms()!!
    }
}