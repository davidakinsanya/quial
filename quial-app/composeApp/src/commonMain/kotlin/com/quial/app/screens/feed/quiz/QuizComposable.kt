package com.quial.app.screens.feed.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.utils.WormIndicator
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun BoxItem(
    quizHolder: QuizStateHolder,
    itemIndex: Int,
    optionState: MutableList<MutableState<Boolean>>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    val click = optionState[itemIndex]

    Box(
        modifier = modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
            .clickable {
                quizHolder.quizOptionState(optionState, itemIndex)
            }
            .background(backgroundColor)
            .border(border = BorderStroke(
                    width = 3.dp,
                    color = if (click.value) Color.White else Color.Black),
                    shape = RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        content.invoke()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OptionsPager(quizHolder: QuizStateHolder) {
    val options = remember { mutableStateOf(quizHolder.quizOptions()) }
    quizHolder.setQuizOptions(options.value)

    val pagerState = rememberPagerState(pageCount = { options.value.size })
    val optionState = quizHolder.listOfStates(options.value.size)
    val alphabet = listOf("A", "B", "C")


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WormIndicator(pagerState = pagerState)

        HorizontalPager(
            state = pagerState,
            pageSpacing = 80.dp,
        ) { index ->
            Column {
                val option = options.value[index]

                val idiomText = option.info[0]
                val idiomMeaning = option.meaning[0]

               val text = if (idiomMeaning.isNotEmpty()) {
                   quizHolder.splitText(idiomMeaning)[0]
               } else {
                   quizHolder.splitText(idiomText)[1]
               }

                BoxItem(
                    quizHolder = quizHolder,
                    itemIndex = index,
                    optionState = optionState,
                    backgroundColor = Color.Transparent,
                    content = {
                        Column(modifier = Modifier.fillMaxSize(),
                               verticalArrangement = Arrangement.Center,
                               horizontalAlignment = Alignment.CenterHorizontally) { // "${ABC[index]}\n

                            Text(
                                text =
                                alphabet[index],
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = text,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 2.dp, start = 12.dp, end = 12.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}