package com.quial.app.screens.onboarding.comps

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.screens.onboarding.OnboardingUiStateHolder
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingNavigationButtons(state: PagerState,
                                navigate: () -> Unit,
                                uiStateHolder: OnboardingUiStateHolder,
                                dataStateHolder: DataStoreStateHolder) {
    Row {
        if (state.currentPage == state.pageCount - 1)
            Button(onClick = {
                dataStateHolder.saveOnboardingResponse(uiStateHolder.getOnboardingResponse())
                navigate.invoke() },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(Color.White)
                   ) {
                Text(text = "Continue",
                     color = Color.Black,
                     fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
            }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WormIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    indicatorWidth: Dp = 9.dp,
    spacing: Dp = indicatorWidth,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = modifier
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pagerState.pageCount) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                )
            }
        }

        Box(
            Modifier
                .wormTransition(pagerState, indicatorWidth)
                .size(10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.wormTransition(
    pagerState: PagerState,
    indicatorWidth: Dp
) =
    drawBehind {
        val distance = size.width + indicatorWidth.roundToPx()
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        val wormOffset = (scrollPosition % 1) * 2

        val xPos = scrollPosition.toInt() * distance
        val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
        val tail = xPos + size.width + 1f.coerceAtMost(wormOffset) * distance

        val worm = RoundRect(
            head, 0f, tail, size.height,
            CornerRadius(50f)
        )

        val path = androidx.compose.ui.graphics.Path().apply { addRoundRect(worm) }
        drawPath(path = path, color = Color.Black)
    }