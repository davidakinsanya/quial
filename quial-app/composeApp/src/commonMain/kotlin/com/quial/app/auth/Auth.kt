package com.quial.app.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButtonIconOnly
import com.mmk.kmpauth.uihelper.google.GoogleSignInButtonIconOnly
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = 50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        //Google Sign-In Button and authentication with Firebase
        GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
            GoogleSignInButtonIconOnly(
                modifier = modifier.size(75.dp),
                onClick = { this.onClick() },
               // mode = GoogleButtonMode.Dark
            )
        }

        Spacer(modifier.padding(40.dp))

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