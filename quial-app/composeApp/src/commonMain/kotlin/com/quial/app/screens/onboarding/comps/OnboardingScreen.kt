package com.quial.app.screens.onboarding.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.data.onboarding.Question
import com.quial.app.data.onboarding.Statement
import com.quial.app.images.QuialImage
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import com.quial.app.utils.WormIndicator
import dev.gitlive.firebase.analytics.logEvent
import io.ktor.http.parameters

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    uiStateHolder: OnboardingUiStateHolder,
    dataHolder: DataStoreStateHolder,
    onNavigateMain: () -> Unit,
    analytics: FirebaseAnalytics,
) {
    Scaffold(modifier = modifier
        .fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {
        val map by uiStateHolder.onboardingMap.collectAsState()
        val pagerState = rememberPagerState(pageCount = { map.size })

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuialImage(modifier.size(130.dp))
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 70.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 100.dp
            ) { index ->

                Box(
                    modifier = modifier
                        .border(
                            border = BorderStroke(3.dp, Color.Black),
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
                    if (uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty()) {
                        if (uiStateHolder.questionObjectCheck(map[index])) {
                            val question = map[index] as Question
                            QuestionComposable(modifier, question, uiStateHolder)

                            analytics.logEvent("question_onboarding_view") {
                                parameters {
                                    param("question", question.getOnboardingQuestion())
                                    param("options", question.getOnboardingOptions().toString())
                                }
                            }

                        } else {
                            val statement = map[index] as Statement
                            StatementComposable(modifier, statement)

                            analytics.logEvent("statement_onboarding_view") {
                                parameters {
                                    param("header", statement.getOnboardingHeader())
                                    param("text", statement.getOnboardingText())
                                }
                            }
                        }
                    } else {
                        //
                    }
                }
            }

            Spacer(modifier.padding(10.dp))

            if (pagerState.currentPage != pagerState.pageCount - 1
                && uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty()
            )
                WormIndicator(pagerState = pagerState)

            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiStateHolder.onboardingMap.collectAsState().value.isNotEmpty()) {
                    OnboardingNavigationButtons(
                        state = pagerState,
                        navigate = onNavigateMain,
                        dataStateHolder = dataHolder,
                        uiStateHolder = uiStateHolder
                    )
                }
            }
        }
    }
}