package com.quial.app.screens.feed.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.quial.app.navigation.RootAppDestination
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res
import secrets.BuildConfig

@Composable
fun OptionsMenu(premiumBool: Boolean,
                showMenu: MutableState<Boolean>,
                modifier: Modifier) {
    val navigator = LocalNavigator.current
    val uriHandler = LocalUriHandler.current

    Box(contentAlignment = Alignment.TopEnd,
        modifier = modifier.padding(end = 20.dp)) {

            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false },
                modifier = modifier
            ) {

                if (premiumBool) {
                    DropdownMenuItem(onClick = {
                        showMenu.value = !showMenu.value
                        navigator?.push(RootAppDestination.Paywall)
                    }) {
                        Text(
                            text = "Upgrade",
                            fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                            color = Color.Red
                        )
                    }
                }

                DropdownMenuItem(onClick = {
                    showMenu.value = !showMenu.value
                    uriHandler.openUri(BuildConfig.FEEDBACK_URL)
                }) {
                    Text(
                        text = "Feedback",
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }

                DropdownMenuItem(onClick = {
                    showMenu.value = !showMenu.value
                    uriHandler.openUri(BuildConfig.APP_STORE_RATING_ANDROID)
                }) {
                    Text(
                        text = "Rating",
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }

                DropdownMenuItem(onClick = {
                    showMenu.value = !showMenu.value
                    uriHandler.openUri(BuildConfig.LINKTREE_URL)
                }) {
                    Text(
                        text = "Follow",
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }
            }
    }
}