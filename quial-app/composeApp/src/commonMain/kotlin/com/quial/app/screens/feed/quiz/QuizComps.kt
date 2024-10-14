package com.quial.app.screens.feed.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.quial.app.utils.WormIndicator

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
fun OptionsPager(
    quizHolder: QuizStateHolder,
    items: List<Any>// replace with your data
) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    val optionState = quizHolder.listOfStates(items.size)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Spacer(modifier = modifier.padding(10.dp))
        WormIndicator(pagerState = pagerState)

        HorizontalPager(
            state = pagerState,
            pageSpacing = 80.dp,
        ) { index ->
            Column {
                BoxItem(
                    quizHolder = quizHolder,
                    itemIndex = index,
                    optionState = optionState,
                    backgroundColor = Color.Transparent,
                    content = {
                        // Add your content here (e.g., Text, Image, etc.)
                    }
                )
            }
        }
    }
}