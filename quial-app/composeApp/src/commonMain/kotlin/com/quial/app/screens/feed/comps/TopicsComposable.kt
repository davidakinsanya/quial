package com.quial.app.screens.feed.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.quial.app.screens.feed.FeedUiStateHolder
import quial_app.composeapp.generated.resources.DMSans_Regular
import quial_app.composeapp.generated.resources.Res

@Composable
fun TopicsComposable(feedHolder: FeedUiStateHolder,
                     isPremium: Boolean) {

    val topics = feedHolder.getTopics(isPremium)

    ScrollableTabRow(selectedTabIndex = 0,
                     modifier = Modifier
                         .fillMaxWidth(0.5f)
                         .height(100.dp)
                         .padding(end = 5.dp),
                     edgePadding = 3.dp,
                     indicator = {},
                     backgroundColor = Color.Transparent
        ) {
        topics.listOfTopics.forEach {
            val isSelected = topics.listOfStates[topics.listOfTopics.indexOf(it)].value
            Button(
                onClick = it.onClick,
                shape = RoundedCornerShape(30.dp),
                colors = if (isSelected) {
                    ButtonDefaults.outlinedButtonColors(Color.White)
                } else {
                    ButtonDefaults.outlinedButtonColors(Color(125, 184, 107))
                },
                modifier = Modifier.padding(2.dp),
                border = BorderStroke(1.5.dp, Color.Black)
            ) {
                Text(text = it.topic,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(Res.font.DMSans_Regular)))
            }
        }
    }
}