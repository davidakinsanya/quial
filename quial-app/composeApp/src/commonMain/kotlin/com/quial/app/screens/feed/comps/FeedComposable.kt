package com.quial.app.screens.feed.comps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.data.idiom.Idiom
import com.quial.app.screens.feed.FeedUiStateHolder
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import network.chaintech.composeMultiplatformScreenCapture.ScreenCaptureComposable
import nl.marc_apps.tts.TextToSpeechInstance
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun FeedComposable(modifier: Modifier,
                   idiom: Idiom,
                   uiHolder: FeedUiStateHolder,
                   isNotPremium: Boolean,
                   analytics: FirebaseAnalytics,
                   tts: TextToSpeechInstance?) {


    val textSize = 20.sp
    val font =  FontFamily(Font(Res.font.DMSans_Bold))
    val idiomText = idiom.info[0]
    val idiomMeaning = idiom.meaning[0]
    val idiomExample = idiom.exampleSentences[0]

    ScreenCaptureComposable(
        modifier = Modifier,
        screenCaptureController = uiHolder.getScreenShotState()!!,
        shareImage = true,
        onCaptured = { img, _ ->
            println("CAPTURED:: ${img == null}")
            if (img != null) {
                uiHolder.captureScreenShot(img)
            }
        }
    ) {}

    Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.Start) {
            if (isNotPremium) {
                WebsiteButton(analytics)
            } else {
                TTSButton(tts, uiHolder)
            }
        }

        Text(
            text = uiHolder.splitText(idiomText)[0],
            textDecoration = TextDecoration.Underline,
            modifier = modifier
                .padding(start = 10.dp)
                .fillMaxWidth(0.9f)
                .blur(if (isNotPremium) 8.dp else 0.dp),
            textAlign = TextAlign.End,
            fontFamily = font
        )
    }

    Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Meaning:",
                    modifier = modifier.fillMaxWidth().padding(bottom = 5.dp),
                    fontSize = textSize,
                    textAlign = TextAlign.End,
                    fontFamily = font
                )

                if (idiomMeaning.isNotEmpty()) {
                    repeat(idiom.meaning.size) { index ->
                        Text(
                            text = uiHolder.splitText(idiomMeaning)[index],
                            modifier = modifier
                                .fillMaxWidth()
                                .blur(radius = if (isNotPremium) 8.dp else 0.dp),
                            fontSize = textSize,
                            textAlign = TextAlign.End,
                            fontFamily = font
                        )
                    }
                } else {
                    Text(
                        text = uiHolder.splitText(idiomText)[1],
                        modifier = modifier
                            .fillMaxWidth()
                            .blur(radius = if (isNotPremium) 8.dp else 0.dp),
                        fontSize = textSize,
                        textAlign = TextAlign.End,
                        fontFamily = font
                    )
                }
            }
        }


        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp, bottom = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Example:",
                    modifier = modifier.fillMaxWidth().padding(bottom = 5.dp),
                    fontSize = textSize,
                    textAlign = TextAlign.End,
                    fontFamily = font
                )
                if (idiomExample.isNotEmpty()) {
                    repeat(idiom.exampleSentences.size) { index ->
                        Text(
                            text = "'" + uiHolder.splitText(idiomExample)[index] + "'",
                            modifier = modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxWidth()
                                .blur(radius = if (isNotPremium) 8.dp else 0.dp)
                                .padding(bottom = 5.dp),
                            textAlign = TextAlign.End,
                            fontSize = textSize,
                            fontFamily = font
                        )
                    }
                } else {
                    val strings = uiHolder.splitText(idiomText)
                    val edgeCaseIndex = if (strings.size == 3) 2 else 0

                    Text(
                        text = "'" + strings[edgeCaseIndex] + "'",
                        modifier = modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .blur(radius = if (isNotPremium) 8.dp else 0.dp)
                            .padding(bottom = 5.dp),
                        textAlign = TextAlign.End,
                        fontSize = textSize,
                        fontFamily = font
                    )
                }
            }
        }
    }
}