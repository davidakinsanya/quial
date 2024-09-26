package com.quial.app.screen.onboarding

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quial.app.data.onboarding.Question
import com.quial.app.data.onboarding.Statement

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    uiStateHolder: OnboardingUiStateHolder,
    onNavigateMain: () -> Unit
) {
    Scaffold(modifier = modifier
        .fillMaxSize()
    ) {
        val map by uiStateHolder.onboardingMap.collectAsState()
        val pagerState = rememberPagerState(pageCount = { map.size })
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 70.dp, bottom = 80.dp, start = 25.dp, end = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 100.dp
                ) { index ->

                    Box(
                        modifier = modifier
                            .border(border = BorderStroke(1.dp, Color.Black))
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f),
                        contentAlignment = Alignment.Center
                    ) {

                        Box(
                            modifier = modifier
                                .padding(
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    start = 50.dp,
                                    end = 50.dp
                                )
                        ) {

                            if (uiStateHolder.questionObjectCheck(map[index])) {
                                val question = map[index] as Question
                                println(question.question)
                                Text(text = question.question, textAlign = TextAlign.Center)
                            } else {
                                val statement = map[index] as Statement
                                println(statement.header)
                                Text(text = statement.header, textAlign = TextAlign.Center)
                            }

                        }
                    }
                }

                if (uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty()) {
                    Spacer(modifier = modifier.padding(top = 40.dp))
                    OnboardingNavigationButtons()
                }

            }
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingNavigationButtons() {
    Row() {
        Button(onClick = {  }) {
            Text(text = "Continue")
        }
    }
}