package com.quial.app.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import quial_app.composeapp.generated.resources.Res
import quial_app.composeapp.generated.resources.quial_img
import quial_app.composeapp.generated.resources.sign_in_img

@Composable
fun QuialImage(modifier: Modifier) {
    Image(painter = painterResource(Res.drawable.quial_img),
          contentDescription = "",
          contentScale = ContentScale.Crop,
          modifier = modifier)
}

@Composable
fun SignInImage(modifier: Modifier) {
    Image(painter = painterResource(Res.drawable.sign_in_img),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier)
}