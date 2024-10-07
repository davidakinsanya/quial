package com.quial.app.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import com.mmk.kmpauth.google.GoogleUser
import com.mmk.kmpauth.uihelper.apple.AppleSignInButtonIconOnly
import com.mmk.kmpauth.uihelper.google.GoogleSignInButtonIconOnly
import com.quial.app.images.QuialImage
import com.quial.app.images.SignInImage
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
    onGoogleSignInResult: (GoogleUser?) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {

        Column(modifier = modifier,
               verticalArrangement = Arrangement.spacedBy(150.dp),
               horizontalAlignment = Alignment.CenterHorizontally) {

            QuialImage(modifier.fillMaxWidth())

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                //Google Sign-In Button and authentication with Firebase
                GoogleButtonUiContainer(onGoogleSignInResult = onGoogleSignInResult) {
                    GoogleSignInButtonIconOnly(
                        modifier = modifier.size(75.dp),
                        onClick = { this.onClick() },
                        // mode = GoogleButtonMode.Dark
                    )
                }

                SignInImage(modifier.size(150.dp))

                //Apple Sign-In Button and authentication with Firebase
                AppleButtonUiContainer(onResult = onFirebaseResult) {
                    AppleSignInButtonIconOnly(
                        modifier = modifier.size(75.dp),
                        onClick = { this.onClick() },
                        // mode = AppleButtonMode.Black
                    )
                }
            }
        }
    }
}