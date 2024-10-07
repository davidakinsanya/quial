package com.quial.app.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.quial.app.navigation.RootAppDestination
import com.quial.app.navigation.RootAppNavigation
import com.quial.app.screen.auth.AuthUiHelperButtonsAndFirebaseAuth
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    MaterialTheme {
        // RootAppNavigation(true)
        AuthUiHelperButtonsAndFirebaseAuth(modifier = Modifier, onFirebaseResult = {
           googleUser ->
            runBlocking {
                val idToken = googleUser.getOrNull()?.getIdToken(false)
            }
        })
    }
}


@Composable
fun RootAppNavigation(isOnBoardShown: Boolean) {
    val bool = isOnBoardShown
    val startDestination = RootAppDestination.Onboarding
    RootAppNavigation(startDestination = startDestination)
}