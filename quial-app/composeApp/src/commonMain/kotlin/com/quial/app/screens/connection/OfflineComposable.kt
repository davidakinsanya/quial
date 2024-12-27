package com.quial.app.screens.connection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quial.app.images.QuialImage
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@Composable
fun OfflineComposable(modifier: Modifier
) {
    Scaffold(modifier = modifier
        .fillMaxSize(),
        backgroundColor = Color(125, 184, 107)
    ) {
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
                .padding(top = 100.dp, bottom = 10.dp, start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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

                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "Co",
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                    Text(
                        modifier = modifier.padding(start = 10.dp).fillMaxWidth(0.9f),
                        text = "Connection",
                        textAlign = TextAlign.End,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "You are currently not connected to the internet.",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }
            }
        }
    }
}