package com.quial.app.screen.onboarding.comps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomizedCheckbox(isChecked: MutableState<Boolean>,
                       optionState: MutableList<MutableState<Boolean>>,
                       index: Int,
                       modifier: Modifier) {
    Checkbox(
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = !isChecked.value
            stateChecked(optionState, index) },
        modifier = modifier.padding(8.dp).background(Color.Transparent)
    )
}

fun stateChecked(optionState: MutableList<MutableState<Boolean>>, indexChecked: Int) {
    for (i in 0..< optionState.size) {
        if (i != indexChecked) {
            optionState[i].value = false
        }
    }
}