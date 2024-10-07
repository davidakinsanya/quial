package com.quial.app.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.quial.app.navigation.RootAppDestination
import com.quial.app.navigation.RootAppNavigation
import com.quial.app.screen.auth.AuthUiHelperButtonsAndFirebaseAuth
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    MaterialTheme {
        RootAppNavigation(true)
        /*
        AuthUiHelperButtonsAndFirebaseAuth(modifier = Modifier,
            onGoogleSignInResult = { googleUser -> println(googleUser?.idToken) },
            onFirebaseResult = {})

         */
    }
}


@Composable
fun RootAppNavigation(isOnBoardShown: Boolean) {
    val bool = isOnBoardShown
    val startDestination = RootAppDestination.Onboarding
    RootAppNavigation(startDestination = startDestination)
}