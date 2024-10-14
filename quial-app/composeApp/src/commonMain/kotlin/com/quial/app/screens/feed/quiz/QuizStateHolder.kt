package com.quial.app.screens.feed.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.quial.app.data.idiom.Idiom
import com.quial.app.utils.UiStateHolder

class QuizStateHolder: UiStateHolder() {

    private val listOfIdioms = mutableListOf<Idiom>()
    private var idiomGuess: Idiom? = null
    private val listOfStates = mutableListOf<MutableState<Boolean>>()

    @Composable
    fun listOfStates(index: Int): MutableList<MutableState<Boolean>> {
        for (i in 0..index) {
            listOfStates.add(remember { mutableStateOf(false) })

        }
        return listOfStates
    }

    fun getTrueState(): Int {
        var index = 0
        for (state in this.listOfStates) {
            if (state.value) {
                index = this.listOfStates.indexOf(state)
            }
        }
        return index
    }

    fun quizOptionState(
        optionState: MutableList<MutableState<Boolean>>,
        indexChecked: Int
    ) {
        for (i in 0..<optionState.size) {
            optionState[i].value = i == indexChecked
        }
    }

    fun addToQuiz(idiom: Idiom) {
        listOfIdioms.add(idiom)
        println(listOfIdioms.size)
    }

    fun getQuizMaterial(): List<Idiom> {
        return listOfIdioms
    }

    fun setIdiomGuess() {
        this.idiomGuess = this.getQuizMaterial()[(0..<this.listOfIdioms.size).random()]
    }

    fun getOptions(): List<Idiom> {
        val newList = mutableListOf<Idiom>()
        if (idiomGuess != null)
            newList.add(idiomGuess!!)
        this.getQuizMaterial().filter { it != idiomGuess }.map {
            newList.add(it)
        }
        newList.reversed()
        return  newList.toSet().toList().take(4)
    }

    fun getIdiomGuess(): Idiom {
        return idiomGuess!!
    }

    fun resetQuiz() {
        this.idiomGuess = null
        this.listOfIdioms.clear()
        this.listOfStates.clear()
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
}