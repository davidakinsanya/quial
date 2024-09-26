package com.quial.app.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        .padding(top = 20.dp)
    ){
        val map by uiStateHolder.onboardingMap.collectAsStateWithLifecycle()
        val pagerState = rememberPagerState(pageCount = { uiStateHolder.objectCount() })

        HorizontalPager(state = pagerState) { index ->
            Box (modifier = modifier,
                contentAlignment = Alignment.Center) {
                if (uiStateHolder.questionObjectCheck(map[index])) {
                    val question = map[index] as Question
                    Text(text = question.question)
                } else {
                    val statement = map[index] as Statement
                    Text(text = statement.header)
                }
            }
        }

    }
}