package com.quial.app.screens.feed.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.quial.app.data.idiom.Idiom
import com.quial.app.utils.UiStateHolder

class QuizStateHolder(): UiStateHolder() {

    private val listOfIdioms = mutableListOf<Idiom>()

    @Composable
    fun listOfStates(index: Int): MutableList<MutableState<Boolean>> {
        val listOfStates = mutableListOf<MutableState<Boolean>>()
        for (i in 0..index) {
            listOfStates.add(remember {  mutableStateOf(false) })

        }
        return listOfStates
    }

    fun quizOptionState(optionState: MutableList<MutableState<Boolean>>,
                        indexChecked: Int
    ) {
        for (i in 0..< optionState.size) {
            optionState[i].value = i == indexChecked
        }
    }

    fun addToQuiz(idiom: Idiom) {
        listOfIdioms.add(idiom)
    }

    fun getQuizMaterial(): List<Idiom> {
        return listOfIdioms
    }
}