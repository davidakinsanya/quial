package com.quial.app.screens.feed.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.quial.app.data.idiom.Idiom
import com.quial.app.utils.UiStateHolder

class QuizStateHolder: UiStateHolder() {

    private val listOfIdioms = mutableStateOf(mutableListOf<Idiom>())
    private var idiomGuess: Idiom? = null
    private val listOfStates = mutableListOf<MutableState<Boolean>>()
    private val quizOptions = mutableStateOf(listOf<Idiom>())
    private val correctAnswer = mutableStateOf<Boolean?>(null)
    private val index = mutableStateOf((0..5).random())
    private val trueState = mutableStateOf(0)

    @Composable
    fun listOfStates(index: Int): MutableList<MutableState<Boolean>> {
        for (i in 0..index) {
            listOfStates.add(remember { mutableStateOf(false) })

        }
        return listOfStates
    }

    private fun getTrueState(): Int {
        for (state in this.listOfStates) {
            if (state.value) {
                trueState.value = this.listOfStates.indexOf(state)
            }
        }
        return trueState.value
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
        listOfIdioms.value.add(idiom)
    }

     fun getQuizMaterial(): List<Idiom> {
        return listOfIdioms.value
    }

    fun getQuizOptions(): List<Idiom> {
        return quizOptions.value
    }

    fun setQuizOptions(list: List<Idiom>) {
        quizOptions.value = list
    }

    fun setIdiomGuess() {
        val material = this.getQuizMaterial()
        this.idiomGuess = material[index.value]
    }

    fun quizOptions(index: List<Int>): List<Idiom> {
        val newList = mutableListOf<Idiom>()
        val list = (0..2).toList()
        val material = this.getQuizMaterial()

        newList.add(material[index[0]])
        newList.add(material[index[1]])
        newList.add(material[index[2]])
        newList.add(material[index[2]])

        val pos = list.random()
        if (idiomGuess != null) newList[pos] = idiomGuess!!
        return newList.toSet().toList()
    }

    fun getIdiomGuess(): Idiom {
        return idiomGuess!!
    }

    fun assessAnswer(): Boolean {
        if (idiomGuess != null)  println(getIdiomGuess())

        val index = if (getTrueState() == quizOptions.value.size)
            getTrueState() - 1
        else getTrueState()

        // println(index)

        correctAnswer.value = quizOptions.value[index] == getIdiomGuess()
        return quizOptions.value[index] == getIdiomGuess()
    }

    fun getAnswerState(): Boolean? {
        return correctAnswer.value
    }

    fun answerReset() {
        this.listOfStates.clear()
        if (correctAnswer.value != null) this.listOfIdioms.value.clear()
        correctAnswer.value = null
        this.idiomGuess = null
        index.value = (0..5).random()
        trueState.value = 0
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