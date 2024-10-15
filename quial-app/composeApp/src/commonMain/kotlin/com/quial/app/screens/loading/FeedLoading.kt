package com.quial.app.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.quial.app.utils.shimmerEffect

@Composable
fun FeedLoadingScreen() {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(20.dp)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )
        Spacer(Modifier.padding(50.dp))
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(20.dp)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )

    }
    Column(verticalArrangement = Arrangement.SpaceBetween) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                TextShimmer()
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 20.dp, bottom = 5.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextShimmer()
            }
        }
    }
}

@Composable
fun TextShimmer() {
    Column {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(25.dp)
                .align(Alignment.End)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )

        Spacer(Modifier.padding(5.dp))

        Box(
            modifier = Modifier
                .width(170.dp)
                .height(25.dp)
                .align(Alignment.End)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )
        Spacer(Modifier.padding(2.dp))

        Box(
            modifier = Modifier
                .width(220.dp)
                .height(25.dp)
                .align(Alignment.End)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )

        Spacer(Modifier.padding(2.dp))

        Box(
            modifier = Modifier
                .width(250.dp)
                .height(25.dp)
                .align(Alignment.End)
                .clip(RectangleShape)
                .then(Modifier.shimmerEffect())
        )
    }
}