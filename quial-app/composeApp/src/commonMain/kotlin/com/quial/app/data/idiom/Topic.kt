package com.quial.app.data.idiom

import androidx.compose.runtime.MutableState

data class Topic(val topic: String,
                 var onClick: () -> Unit = {}) {

}

data class TopicSelected(val listOfTopics: List<Topic>,
                         val listOfStates: MutableList<MutableState<Boolean>>)
