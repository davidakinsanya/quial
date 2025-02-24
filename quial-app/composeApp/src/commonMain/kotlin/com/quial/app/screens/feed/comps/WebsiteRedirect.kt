package com.quial.app.screens.feed.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Regular
import quial_app.composeapp.generated.resources.Res
import secrets.BuildConfig

@Composable
fun WebsiteButton() {
    val uriHandler = LocalUriHandler.current
    Button(
        onClick = { uriHandler.openUri(BuildConfig.WEBSITE_URL) },
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.outlinedButtonColors(Color(125, 184, 107)),
        modifier = Modifier.padding(2.dp),
        border = BorderStroke(1.5.dp, Color.Black)
    ) {
        Text(text = "More",
            color = Color.Black,
            fontFamily = FontFamily(Font(Res.font.DMSans_Regular))
        )
    }
}