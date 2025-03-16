package com.quial.app.screens.feed.sheet


import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.quial.app.images.ArrowUp
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
            ArrowUp(modifier = Modifier.alpha(
                if (uiStateHolder.getPagerState()?.isScrollInProgress == true) 0.4f else 1f
            )
                .fillMaxSize(0.075f)
                .padding(bottom = 35.dp)
                .clickable {
                    scope.launch {
                        showBottomSheet = true
                        sheetState.show()
                    }
                })
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
            .background(Color(125, 184, 107))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("This is a simple bottom sheet", fontFamily = FontFamily(Font(Res.font.DMSans_Bold)))
        Spacer(modifier = Modifier.height(16.dp))
    }
}