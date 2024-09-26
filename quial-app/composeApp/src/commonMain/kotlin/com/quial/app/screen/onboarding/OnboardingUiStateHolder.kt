package com.quial.app.screen.onboarding

import com.quial.app.data.onboarding.Question
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private fun loadData() = uiStateHolderScope.launch {
        _onboardingMap.value = uiState.value.loadData()
    }

    fun objectCount(): Int {
        return _onboardingMap.value.size
    }

    fun questionObjectCheck(obj: Any): Boolean {
        return (obj is Question)
    }

    fun onClickNavigateNext() = uiStateHolderScope.launch {
        /**
        if (_uiState.value.isPrivacyPolicyChecked.not()) return@launch
        withContext(Dispatchers.IO) {
            userPreferences.putBoolean(UserPreferences.KEY_IS_ONBOARD_SHOWN, true)
        }
        _uiState.update { it.copy(onBoardIsShown = true) }
        **/

        // TODO: Figure out what to do with this portion of code.
    }
}