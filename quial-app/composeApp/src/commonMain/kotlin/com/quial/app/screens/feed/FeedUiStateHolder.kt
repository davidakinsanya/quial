package com.quial.app.screens.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.quial.app.data.idiom.Idiom
import com.quial.app.data.idiom.Topic
import com.quial.app.data.idiom.TopicSelected
import com.quial.app.screens.feed.comps.UiState
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FeedUiStateHolder(
    feedUiState: FeedUiState
): UiStateHolder() {
    private val _uiState = MutableStateFlow(feedUiState)

    private val _loadingState = MutableStateFlow<UiState>(UiState.Loading)
    val loadingState: StateFlow<UiState> = _loadingState

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
        _loadingState.value = UiState.Success(_idiomsList.value)
    }

     @Composable
     fun getTopics(): TopicSelected {
         val list = mutableListOf<Topic>()
         var selectionStateSize = 0

         runBlocking {
             selectionStateSize = _uiState.value.retrieveTopics().size + 1
         }

         val selectionState = this.listOfStates(selectionStateSize)

         val forYouTopic = Topic(topic = "for you")
         list.add(forYouTopic)
         forYouTopic.onClick = {
             uiCheckBoxState(selectionState, list.indexOf(forYouTopic))
             // _loadingState.value = UiState.Loading
             // loadData()
         }

         runBlocking {
            _uiState.value.retrieveTopics().forEach { topic ->
                val topicObj = Topic(
                    topic = topic
                )

                list.add(topicObj)

                topicObj.onClick = {
                    runBlocking {
                        uiCheckBoxState(selectionState, list.indexOf(topicObj))
                        // _loadingState.value = UiState.Loading
                        // _idiomsList.value = _uiState.value.getIdiomsByTopic(topicObj.topic)!!
                        // _loadingState.value = UiState.Success(_idiomsList.value)
                    }
                }
            }
         }

        return TopicSelected(list, selectionState)
     }

    @Composable
    fun listOfStates(index: Int): MutableList<MutableState<Boolean>> {
        val listOfStates = mutableListOf<MutableState<Boolean>>()
        for (i in 0..index) {
            listOfStates.add(remember {  mutableStateOf(false) })

        }
        return listOfStates
    }

    private fun uiCheckBoxState(optionState: MutableList<MutableState<Boolean>>,
                        indexChecked: Int
    ) {
        for (i in 0..< optionState.size) {
            optionState[i].value = i == indexChecked
        }
    }

    fun splitText(text: String): List<String> {
        val extractedText =  text.split("', ", "\", ")[0] + "'"
        val alteredText = listOf(
            extractedText
                .substring(1, extractedText.length - 1)
                .replace("\"", "")
                .replace("Meaning:", "")
                .replace("Example:", "")
                .capitalizeFirstLetter()
        )

        return alteredText
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