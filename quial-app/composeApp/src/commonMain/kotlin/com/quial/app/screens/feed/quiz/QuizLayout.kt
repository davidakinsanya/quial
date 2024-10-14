package com.quial.app.screens.feed.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun QuizLayout(
    quizHolder: QuizStateHolder,
    modifier: Modifier = Modifier
) {

    val font =  FontFamily(Font(Res.font.DMSans_Bold))

    quizHolder.setIdiomGuess()
    val idiomText = quizHolder.getIdiomGuess().info[0]
    val cleanText = quizHolder.splitText(idiomText)[0]

    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Text(
            text = "Qu",
            fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
        )

        Text(
            text = "Quiz",
            textDecoration = TextDecoration.Underline,
            modifier = modifier
                .padding(start = 10.dp)
                .fillMaxWidth(0.9f),
            textAlign = TextAlign.End,
            fontFamily = font
        )
    }


    Column(verticalArrangement = Arrangement.SpaceEvenly,
           modifier = modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                   modifier = modifier.fillMaxSize().padding(top = 55.dp)) {
                Text(
                    text = "Guess The Meaning Of .. ",
                    textAlign = TextAlign.Center,
                    fontFamily = font,
                    fontSize = 18.sp,
                    modifier = modifier
                        .padding(top = 35.dp, bottom = 5.dp))

                Text(
                    text = cleanText,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = font,
                    fontSize = 20.sp,
                    modifier = modifier
                        .padding(top = 10.dp, bottom = 5.dp))
            }
        }

        Box(contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.SpaceEvenly,
                   horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier.padding(35.dp))
                OptionsPager(quizHolder = quizHolder)
                Spacer(modifier.padding(10.dp))
                ContinueButton()
            }
        }
    }
}
@Composable
fun ContinueButton() {
    Row {
        Button(onClick = {},
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.White)
        ) {
            Text(text = "Continue",
                color = Color.Black,
                fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
            }
    }
}