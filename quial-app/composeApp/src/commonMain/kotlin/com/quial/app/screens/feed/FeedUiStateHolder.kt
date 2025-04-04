package com.quial.app.screens.feed

import androidx.compose.foundation.pager.PagerState
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedUiStateHolder(
    feedUiState: FeedUiState
): UiStateHolder() {
    private val _uiState = MutableStateFlow(feedUiState)
    private val _loadingState = MutableStateFlow<UiState>(UiState.Loading)
    val loadingState: StateFlow<UiState> = _loadingState

    private val _ttsState = MutableStateFlow("")

    private var pagerState: PagerState? = null

    private val _idiomsList: MutableStateFlow<List<Idiom>> = MutableStateFlow(listOf())
    val idiomsList = _idiomsList
        .onStart { loadData() }
        .stateIn(
        uiStateHolderScope,
        SharingStarted.WhileSubscribed(5000L),
        listOf()
    )

    private val _currentIdiom: MutableStateFlow<Idiom> = MutableStateFlow(Idiom())
    val currentIdiom = _currentIdiom

    private val _isQuiz: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isQuiz = _isQuiz

    private fun loadData() = uiStateHolderScope.launch(Dispatchers.IO) {
        val list = _uiState.value.retrieveIdioms()!!
        if (list.isNotEmpty()) {
            _idiomsList.value = list
            _loadingState.value = UiState.Success(_idiomsList.value)
        }
    }

    suspend fun loadSavedIdioms(savedIdioms: List<String>,
                                isIdiomSaved: MutableState<Boolean>) = withContext(Dispatchers.IO) {

        _loadingState.value = UiState.Loading

        var bool = false
        _idiomsList.value.forEach {
            bool = savedIdioms.contains(it.info[0].split(",")[0])
        }

        if (!bool && savedIdioms.size != _idiomsList.value.size) {

            val savedIdiomsObj = _uiState.value.retrieveIdioms()!!.filter {
                savedIdioms.contains(
                    it.info[0]
                        .split(",")[0]
                        .replace("'", "")
                )
            }

            if (savedIdiomsObj.isEmpty()) {
                _idiomsList.value = _uiState.value.retrieveIdioms()!!
            } else {
                _idiomsList.value = savedIdiomsObj
            }
        } else {
            _idiomsList.value = _uiState.value.retrieveIdioms()!!
        }

        _loadingState.value = UiState.Success(_idiomsList.value)
        isIdiomSaved.value = !isIdiomSaved.value
    }

    fun loadPagerState(pagerState: PagerState) {
        this.pagerState = pagerState
    }

    fun getPagerState(): PagerState? {
        return this.pagerState
    }

     fun getTextToSpeech(): String {
        return _ttsState.value
     }

    fun setTextToSpeech(stack: List<String>) = uiStateHolderScope.launch {
        _ttsState.value = ""
        for (item in stack) {
            _ttsState.value += splitText(item)[0]
            if (item != stack[2]) _ttsState.value += "\n"
        }
    }

    fun setCurrentIdiom(idiom: Idiom) {
        _currentIdiom.value = idiom
    }

    fun setQuizState(quizState: Boolean) {
        _isQuiz.value = quizState
    }

     @Composable
     fun getTopics(isPremium: Boolean): TopicSelected {
         val list = mutableListOf<Topic>()
         var selectionStateSize = 0

         uiStateHolderScope.launch {
             selectionStateSize = _uiState.value.retrieveTopics().size + 1
         }

         val selectionState = this.listOfStates(selectionStateSize)

         val forYouTopic = Topic(topic = "for you")
         list.add(forYouTopic)
         forYouTopic.onClick = {
             uiStateHolderScope.launch {
                 uiCheckBoxState(selectionState, list.indexOf(forYouTopic))
                 _loadingState.value = UiState.Loading
                 loadData()
             }
         }

         uiStateHolderScope.launch {
            _uiState.value.retrieveTopics().forEach { topic ->
                val topicObj = Topic(
                    topic = topic
                )

                list.add(topicObj)

                topicObj.onClick = {
                    uiStateHolderScope.launch(Dispatchers.IO) {
                        uiCheckBoxState(selectionState, list.indexOf(topicObj))
                        _loadingState.value = UiState.Loading
                        if (isPremium) {
                            val premiumIdioms = _uiState.value.getIdiomsByTopic(topicObj.topic)!!
                            if (list.isNotEmpty()) {
                                _idiomsList.value = premiumIdioms
                                _loadingState.value = UiState.Success(_idiomsList.value)
                            }
                        }
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
        val extractedText =  text.split("', ")[0] + "'"
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
        return if (isNotEmpty())
            first().uppercase() + substring(1)
        else this
    }
}