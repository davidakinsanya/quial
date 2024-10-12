package com.quial.app.screens.feed

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

    fun splitText(text: String): List<String> {
        val regex = Regex(pattern = "'[^']*'", options = setOf(RegexOption.IGNORE_CASE))
        val doubleQuote = "\""

        return regex.findAll(text).map {
            it.groupValues[0]
                .substring(1, it.groupValues[0].length - 1)
                .replace(doubleQuote, "")
                .replace("Meaning:", "")
                .replace("Example:", "")
                .capitalizeFirstLetter()
        }.toList()
    }

    private fun String.capitalizeFirstLetter(): String {
        return substring(0, 1).uppercase() + substring(1)
    }

    fun randomInt(): Int {
        return (1..2).random()
    }

    private fun getBoolean(bool: Boolean, randomInt: Int, number: Int): Boolean {
        return bool && (randomInt == number)
    }

    fun getListOfBools(bool: Boolean, randomInt: Int): List<Boolean> {
        return listOf(
            getBoolean(bool = bool, randomInt = randomInt, number = 1) ,
            getBoolean(bool = bool, randomInt = randomInt, number = 2),
            getBoolean(bool = bool, randomInt = randomInt, number = 1)
        )
    }
}