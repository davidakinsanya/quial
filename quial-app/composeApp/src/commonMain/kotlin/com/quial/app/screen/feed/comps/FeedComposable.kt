package com.quial.app.screen.feed.comps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.data.idiom.Idiom
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun FeedComposable(modifier: Modifier, idiom: Idiom) {
    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            text = splitText(idiom.info[0])[0].substring(0, 2),
            fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
        )

        Text(
            text = splitText(idiom.info[0])[0],
            textDecoration = TextDecoration.Underline,
            modifier = modifier.padding(start = 10.dp).fillMaxWidth(0.9f),
            textAlign = TextAlign.End,
            fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
        )
    }

    Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.85f)
        ) {
            if (idiom.meaning[0].isNotEmpty()) {
                repeat(idiom.meaning.size) { index ->
                    Text(
                        text = splitText(idiom.meaning[0])[index],
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }
            } else {
                Text(
                    text = splitText(idiom.info[0])[1],
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                )
            }
        }


        Box(
            modifier = modifier
                .fillMaxWidth(0.7f)
                .padding(bottom = 10.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (idiom.exampleSentences[0].isNotEmpty()) {
                repeat(idiom.meaning.size) { index ->
                    Text(
                        text = "'" + splitText(idiom.exampleSentences[0])[index] + "'",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }
            } else {
                Text(
                    text = "'" + splitText(idiom.info[0])[2] + "'",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                )
            }
        }
    }
}

fun splitText(text: String): List<String> {
    val regex = Regex(pattern = "'[^']*'", options = setOf(RegexOption.IGNORE_CASE))
    return regex.findAll(text).map {
        it.groupValues[0]
            .substring(1, it.groupValues[0].length - 1)
            .replace("Meaning:", "")
            .replace("Example:", "")
    }.toList()
}
