package com.quial.app.screens.feed.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.images.SavedButtonInSheet
import com.quial.app.screens.feed.FeedUiStateHolder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedIdiomView(dataHolder: DataStoreStateHolder,
                   uiStateHolder: FeedUiStateHolder,
                   sheetState: ModalBottomSheetState,
                   isIdiomSaved: MutableState<Boolean>
) {

    val scope = rememberCoroutineScope()
    val savedIdiomCount = remember { mutableStateOf(0) }
    val idioms = remember { mutableStateOf(listOf<String>()) }

    scope.launch {
        idioms.value = dataHolder.getSavedIdioms().split(", ").toMutableList()
        if (idioms.value.size > 1) (idioms.value as MutableList<String>).remove(idioms.value.last())
        savedIdiomCount.value = idioms.value.size
    }


    Box(modifier = Modifier.clickable {
        scope.launch {
            sheetState.hide()
            if (idioms.value.isNotEmpty()) uiStateHolder.loadSavedIdioms(idioms.value, isIdiomSaved)
        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SavedButtonInSheet(modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically)
                )
                Text(
                    text = savedIdiomCount.value.toString(),
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
                    "saved",
                    fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}