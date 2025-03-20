package com.quial.app.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import quial_app.composeapp.generated.resources.Res
import quial_app.composeapp.generated.resources.arrow_up
import quial_app.composeapp.generated.resources.more
import quial_app.composeapp.generated.resources.quial_img
import quial_app.composeapp.generated.resources.saved_clicked
import quial_app.composeapp.generated.resources.saved_unclicked
import quial_app.composeapp.generated.resources.search_icon
import quial_app.composeapp.generated.resources.share_icon
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

@Composable
fun ThreeDots(modifier: Modifier, onClick: () -> Unit) {
    Image(painter = painterResource(Res.drawable.more),
          contentDescription = "",
          contentScale = ContentScale.Crop,
          modifier = modifier.clickable {
              onClick.invoke()
          } )
}

@Composable
fun ArrowUp(modifier: Modifier) {
    Image(painter = painterResource(Res.drawable.arrow_up),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier)
}

@Composable
fun SavedButton(modifier: Modifier, clicked: Boolean) {
    Image(painter = painterResource(
        if (clicked)
            Res.drawable.saved_clicked
        else
            Res.drawable.saved_unclicked
    ),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier)
}

@Composable
fun SavedButtonInSheet(modifier: Modifier) {
    Image(painter = painterResource(Res.drawable.saved_clicked),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier)
}

@Composable
fun SearchButton(modifier: Modifier) {
    Image(painter = painterResource(Res.drawable.search_icon),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier)
}