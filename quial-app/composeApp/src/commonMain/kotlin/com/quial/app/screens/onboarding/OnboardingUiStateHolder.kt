package com.quial.app.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.quial.app.data.onboarding.Question
import com.quial.app.screens.onboarding.comps.OnboardingResponse
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OnboardingUiStateHolder(state: OnboardingScreenUiState): UiStateHolder() {
    private val _uiState = MutableStateFlow(state)
    private val uiState = _uiState.asStateFlow()

    private val _onboardingMap: MutableStateFlow<List<Any>> = MutableStateFlow(listOf())
    val onboardingMap = _onboardingMap
        .onStart { loadData() }
        .stateIn(
            uiStateHolderScope,
            SharingStarted.WhileSubscribed(5000L),
            listOf()
        )

    private val _onboardingResponse = MutableStateFlow(mutableListOf<OnboardingResponse>())
    private val onboardingResponse = _onboardingResponse.asStateFlow()

    private fun loadData() = uiStateHolderScope.launch {
        _onboardingMap.value = uiState.value.loadData()
    }

    fun questionObjectCheck(obj: Any): Boolean {
        return (obj is Question)
    }

    @Composable
    fun listOfStates(index: Int): MutableList<MutableState<Boolean>> {
        val listOfStates = mutableListOf<MutableState<Boolean>>()
        for (i in 0..index) {
            listOfStates.add(remember {  mutableStateOf(false) })

        }
        return listOfStates
    }

    fun uiCheckBoxState(optionState: MutableList<MutableState<Boolean>>,
                     indexChecked: Int
    ) {
        for (i in 0..< optionState.size) {
            optionState[i].value = i == indexChecked
        }
    }

    fun getOnboardingResponse(): MutableList<OnboardingResponse> {
        return this.onboardingResponse.value
    }

     fun onboardingResponseState(question: Question, indexChecked: Int) {
         var responseRemoved: OnboardingResponse? = null
         onboardingResponse.value.forEach {
             if (it.question.question == question.question) responseRemoved = it
         }

         if (responseRemoved != null) this.onboardingResponse.value.remove(responseRemoved!!)
         this.onboardingResponse.value.add(OnboardingResponse(question, indexChecked))
     }
}