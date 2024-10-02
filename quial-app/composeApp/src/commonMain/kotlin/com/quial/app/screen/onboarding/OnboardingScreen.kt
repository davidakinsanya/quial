package com.quial.app.screen.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
        .fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {
        val map by uiStateHolder.onboardingMap.collectAsState()
        val pagerState = rememberPagerState(pageCount = { map.size })

        Column(modifier = modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            QuialLogo(modifier)
        }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 100.dp, bottom = 80.dp, start = 25.dp, end = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 100.dp
                ) { index ->

                    Box(
                        modifier = modifier
                            .border(border = BorderStroke(3.dp, Color.Black),
                                    shape = RoundedCornerShape(15.dp)
                            )
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

                Spacer(modifier.padding(10.dp))

                if (pagerState.currentPage != pagerState.pageCount - 1
                    && uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty())
                    WormIndicator(pagerState = pagerState)

                Box(modifier = modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                    contentAlignment = Alignment.Center
                    ) {
                    if (uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty()) {
                        OnboardingNavigationButtons(pagerState)
                    }
                }
            }
        }
    }