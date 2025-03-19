package com.quial.app.screens.feed.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.images.ArrowUp
import com.quial.app.images.SavedButton
import com.quial.app.images.ShareIcon
import com.quial.app.screens.feed.FeedUiStateHolder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetExample(uiStateHolder: FeedUiStateHolder) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.HalfExpanded)
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // Rounded top corners
        sheetContent = {
            BottomSheetContent()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            val alphaValue = if (uiStateHolder.getPagerState()?.isScrollInProgress == true) 0.4f else 0.8f
            val fontSize = 12.sp
            val textModifier = Modifier
                .alpha(alphaValue)
                .padding(top = 5.dp)

            Row(horizontalArrangement = Arrangement.spacedBy(50.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Box(contentAlignment = Alignment.Center) {

                    val onClick: () -> Unit = {
                        uiStateHolder.getScreenShotState()?.capture()
                    }

                    ShareIcon(
                        modifier = Modifier.alpha(alphaValue)
                            .fillMaxSize(0.075f)
                            .padding(bottom = 35.dp)
                            .clickable { onClick.invoke() })

                    Text(text = "Share",
                         modifier = textModifier.clickable { onClick.invoke() },
                         textAlign = TextAlign.Center,
                         fontSize = fontSize,
                         fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
                }

                Box(contentAlignment = Alignment.Center) {

                    val onClick: () -> Unit = {
                        scope.launch {
                            showBottomSheet = true
                            sheetState.show()
                        }
                    }

                    ArrowUp(
                        modifier = Modifier.alpha(alphaValue)
                            .fillMaxSize(0.075f)
                            .padding(bottom = 35.dp)
                            .clickable { onClick.invoke() })

                    Text(text = "More",
                         modifier = textModifier.clickable { onClick.invoke() },
                         textAlign = TextAlign.Center,
                         fontSize = fontSize,
                         fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
                }

                Box(contentAlignment = Alignment.Center) {

                    SavedButton(
                        modifier = Modifier.alpha(alphaValue)
                            .fillMaxSize(0.075f)
                            .padding(bottom = 35.dp)
                            .clickable {

                            },
                        clicked = false
                    )

                    Text(text = "Save",
                         modifier = textModifier,
                         textAlign = TextAlign.Center,
                         fontSize = fontSize,
                         fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
                }

            }
        }
    }



    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        } else {
            showBottomSheet = false
            sheetState.hide()
        }
    }
}

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), color = Color.Black, width = 5.dp)
            .background(Color(125, 184, 107))
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("This is a simple bottom sheet", fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
        Spacer(modifier = Modifier.height(16.dp))
    }
}