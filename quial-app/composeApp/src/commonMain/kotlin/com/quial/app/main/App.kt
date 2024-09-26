package com.quial.app.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.quial.app.http.requests.OnboardingClient
import com.quial.app.repository.OnboardingRepository
import com.quial.app.screen.onboarding.OnboardingScreen
import com.quial.app.screen.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.getUiStateHolder
import io.ktor.client.HttpClient
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import com.quial.app.utils.getUiStateHolder

import quial_app.composeapp.generated.resources.Res
import quial_app.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }

    }

}