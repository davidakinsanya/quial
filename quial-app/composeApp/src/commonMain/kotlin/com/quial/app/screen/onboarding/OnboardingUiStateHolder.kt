package com.quial.app.screen.onboarding

import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingUiStateHolder(private val state: OnboardingScreenUiState): UiStateHolder() {
    private val _uiState = MutableStateFlow(state)
    val uiState = _uiState.asStateFlow()

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