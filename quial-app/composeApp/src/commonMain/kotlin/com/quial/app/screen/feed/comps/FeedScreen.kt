package com.quial.app.screen.feed.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
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
import androidx.datastore.preferences.core.intPreferencesKey
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.images.QuialImage
import com.quial.app.screen.feed.FeedUiStateHolder
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    uiStateHolder: FeedUiStateHolder,
    dataHolder: DataStoreStateHolder,
) {
    Scaffold(modifier = modifier
        .fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {
        val idioms by uiStateHolder.idiomsList.collectAsState()
        val pageCount = idioms.size * 400
        val pagerState = rememberPagerState(pageCount = { pageCount })


        val currentCount by dataHolder.getPref().data.map {
            val countKey = intPreferencesKey("dailyFreeCount")
            it[countKey] ?: 0
        }.collectAsState(0)


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
                .padding(top = 100.dp, bottom = 90.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VerticalPager(state = pagerState,
                          pageSpacing = 10.dp) { index ->

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
                    if (idioms.isNotEmpty()) {
                        dataHolder.dailyFreeCount(currentCount)
                        FeedComposable(idiom = idioms[index % idioms.size],
                                        modifier = modifier,
                                        dataHolder = dataHolder)
                    }
                }

            }
        }
    }
}
