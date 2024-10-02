package com.quial.app.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButtonIconOnly
import com.mmk.kmpauth.uihelper.google.GoogleSignInButtonIconOnly
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import dev.gitlive.firebase.auth.FirebaseUser
import secrets.BuildConfig

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {

        Column(modifier = modifier,
               verticalArrangement = Arrangement.spacedBy(150.dp),
               horizontalAlignment = Alignment.CenterHorizontally) {

            CoilImage(
                modifier = modifier.fillMaxWidth(),
                imageModel = { BuildConfig.QUIAL_LOGO }, // loading a network image or local resource using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                loading = {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = modifier.size(2.dp))
                    }
                }
            )


            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                //Google Sign-In Button and authentication with Firebase
                GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
                    GoogleSignInButtonIconOnly(
                        modifier = modifier.size(75.dp),
                        onClick = { this.onClick() },
                        // mode = GoogleButtonMode.Dark
                    )
                }


                CoilImage(
                    modifier = modifier.size(150.dp),
                    imageModel = { BuildConfig.SIGN_IN_GRAPHIC }, // loading a network image or local resource using an URL.
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    loading = {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = modifier.size(2.dp))
                        }
                    }
                )

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