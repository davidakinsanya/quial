package com.quial.app.screens.onboarding.comps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.quial.app.data.onboarding.Question
import com.quial.app.screens.onboarding.OnboardingUiStateHolder

@Composable
fun CustomizedCheckbox(uiStateHolder: OnboardingUiStateHolder,
                       question: Question,
                       isChecked: MutableState<Boolean>,
                       optionState: MutableList<MutableState<Boolean>>,
                       index: Int,
                       modifier: Modifier) {


    uiStateHolder.getOnboardingResponse().forEach {
        if (it.question.question == question.question
            && it.answerIndex == index) {
            uiStateHolder.uiCheckBoxState(optionState = optionState,
                indexChecked = index)
        }
    }

    Checkbox(
        checked = isChecked.value,

        onCheckedChange = { isChecked.value = !isChecked.value
            uiStateHolder.uiCheckBoxState(optionState, index)
            uiStateHolder.onboardingResponseState(question, index) },

        modifier = modifier.padding(8.dp).background(Color.Transparent)
    )
}