package com.quial.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow

actual fun isAndroid(): Boolean {
    return true
}

@Composable
actual fun <T> StateFlow<T>.asState(): State<T> {
    TODO("Not yet implemented")
}