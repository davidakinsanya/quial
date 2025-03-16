package com.quial.app.screens.feed.sheet


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetExample() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.HalfExpanded)
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // Rounded top corners
        sheetContent = {
            BottomSheetContent()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Icon(Icons.Default.KeyboardArrowUp,
                contentDescription = "Drag Handle",
                tint = Color.White,
                modifier = Modifier.clickable {
                    showBottomSheet = true
                })
        }
    }

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }
}

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("This is a simple bottom sheet")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {  }) {
            Text("Close")
        }
    }
}