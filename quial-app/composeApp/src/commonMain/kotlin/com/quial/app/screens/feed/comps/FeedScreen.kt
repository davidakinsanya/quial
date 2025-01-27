package com.quial.app.screens.feed.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.images.QuialImage
import com.quial.app.images.ThreeDots
import com.quial.app.screens.feed.FeedUiStateHolder
import com.quial.app.screens.feed.dropdown.OptionsMenu
import com.quial.app.screens.feed.quiz.QuizLayout
import com.quial.app.screens.feed.quiz.QuizStateHolder
import com.quial.app.screens.loading.FeedLoadingScreen
import com.quial.app.utils.sameDateCheck
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    uiStateHolder: FeedUiStateHolder,
    dataHolder: DataStoreStateHolder,
    quizHolder: QuizStateHolder,
    appRating: @Composable () -> Unit
) {
    Scaffold(modifier = modifier
        .fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {
        val idioms by uiStateHolder.idiomsList.collectAsState()
        val pageCount = idioms.size * 400
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val correctAnswer = quizHolder.getAnswerState()
        val showMenu = remember { mutableStateOf(false) }

        val borderStroke: BorderStroke = when (correctAnswer) {
            null -> {
                BorderStroke(3.dp, Color.Black)
            }
            false -> {
                BorderStroke(3.dp, Color.Red)
            }
            else -> {
                BorderStroke(6.dp, Color.Green)
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()) {
                QuialImage(modifier.size(130.dp).padding(end = 35.dp))
                ThreeDots(modifier.size(50.dp), { showMenu.value = !showMenu.value })
            }
        }

        Column(modifier = modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.End) {
            OptionsMenu(
                premiumBool = !dataHolder.isPremium(),
                showMenu = showMenu,
                modifier = modifier
            )
        }


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 10.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerticalPager(state = pagerState,
                          pageSpacing = 10.dp) { index ->

                val string by dataHolder.getPref().data.map {
                    val stamp = stringPreferencesKey("timeStamp")
                    it[stamp]?: ""
                }.collectAsState("")

                val stampCheck = sameDateCheck(string)

                if (index > 3 && !stampCheck && !dataHolder.isPremium()) {
                    dataHolder.updateTimeStamp()
                }

                Box(
                    modifier = modifier
                        .border(
                            border = borderStroke,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(
                            top = 30.dp,
                            bottom = 0.dp,
                            start = 20.dp,
                            end = 20.dp
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    contentAlignment = Alignment.TopCenter
                ) {

                    if (idioms.isNotEmpty()) {
                        val idiomView = idioms[index % idioms.size]

                        if (pagerState.currentPage % 7 == 0
                            && pagerState.currentPage >= 7
                            && dataHolder.isPremium()) {

                            quizHolder.setIdiomGuess()
                            val options = remember { mutableStateOf(quizHolder.quizOptions()) }
                            QuizLayout(quizHolder = quizHolder, options = options)

                        } else {

                            if (dataHolder.isPremium()) {
                                quizHolder.answerReset()
                                quizHolder.addToQuiz(idiomView)
                            }

                            FeedComposable(
                                idiom = idiomView,
                                modifier = modifier,
                                uiHolder = uiStateHolder,
                                dataHolder = dataHolder,
                                stampCheck = stampCheck
                            )
                        }
                    } else {
                        FeedLoadingScreen()
                    }
                }
            }
        }
    }
}
