package com.quial.app.screens.feed.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.quial.app.screens.feed.FeedUiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.launch
import nl.marc_apps.tts.TextToSpeechInstance
import org.jetbrains.compose.resources.painterResource
import quial_app.composeapp.generated.resources.Res
import quial_app.composeapp.generated.resources.speak

@Composable
fun TTSButton(tts: TextToSpeechInstance?,
              uiHolder: FeedUiStateHolder) {

    Button(
        onClick = {
            uiHolder.uiStateHolderScope.launch {
                tts?.say(text = uiHolder.getTextToSpeech())
            }
        },
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.outlinedButtonColors(Color(125, 184, 107)),
        modifier = Modifier
            .shadow(15.dp, shape = RoundedCornerShape(20.dp)),
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Image(painter = painterResource(Res.drawable.speak),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
        )
    }
}