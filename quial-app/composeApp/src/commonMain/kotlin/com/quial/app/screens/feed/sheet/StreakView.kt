package com.quial.app.screens.feed.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.images.FireEmoji
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun StreakView(dataHolder: DataStoreStateHolder) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FireEmoji(modifier = Modifier)
                Text(
                    "2",
                    fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Top)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "streak",
                    fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                    fontSize = 14.sp
                    )
            }
        }
    }
}