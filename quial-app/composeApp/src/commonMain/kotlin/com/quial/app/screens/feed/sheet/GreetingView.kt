package com.quial.app.screens.feed.sheet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.utils.getGreeting
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res


@Composable
fun GreetingView(dataHolder: DataStoreStateHolder) {
    Box(contentAlignment = Alignment.TopCenter) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .border(
                        color = Color.Black,
                        width = 2.dp,
                        shape = RoundedCornerShape(50.dp)
                    ),
                model = dataHolder.getImage(), // replace with working URL
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                getGreeting() + ", " + dataHolder.getName() + "!",
                fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
            )
        }
    }
}
