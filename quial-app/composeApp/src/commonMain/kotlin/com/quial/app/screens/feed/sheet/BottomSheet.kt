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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.images.ArrowUp
import com.quial.app.images.SavedButton
import com.quial.app.images.SearchButton
import com.quial.app.screens.feed.FeedUiStateHolder
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import quial_app.composeapp.generated.resources.DMSans_Bold
import quial_app.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetExample(uiStateHolder: FeedUiStateHolder,
                       dataStoreHolder: DataStoreStateHolder) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val currentIdiom by uiStateHolder.currentIdiom.collectAsState()

    val idiomTitle = if (currentIdiom.info.isNotEmpty())
        uiStateHolder.splitText(currentIdiom.info[0])[0].lowercase()
    else
        ""

    val isIdiomSaved = remember {
        mutableStateOf(false)
    }


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // Rounded top corners
        sheetContent = {
            BottomSheetContent(
                dataHolder = dataStoreHolder,
                uiStateHolder = uiStateHolder,
                sheetState = sheetState,
                isIdiomSaved = isIdiomSaved
            )
        }
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            val alphaValue = if (uiStateHolder.getPagerState()?.isScrollInProgress == true) 0.4f else 0.8f
            val quizState by uiStateHolder.isQuiz.collectAsState()
            val altAlphaValue = if (quizState) 0.4f else alphaValue

            val fontSize = 12.sp
            val textModifier = Modifier
                .padding(top = 5.dp, bottom = 1.dp)

            Row(horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Box(contentAlignment = Alignment.Center) {
                    val uriHandler = LocalUriHandler.current

                    val onClick: () -> Unit = {
                        val searchQuery =
                            uiStateHolder.splitText(currentIdiom.info[0])[0].lowercase() + " idiom meaning and synonyms"

                        if (!quizState) {
                            uriHandler.openUri(
                                "https://google.com/search?q=${
                                    searchQuery.replace(
                                        " ",
                                        "+"
                                    )
                                }"
                            )
                        }
                    }

                    SearchButton(
                        modifier = Modifier.alpha(altAlphaValue)
                            .fillMaxSize(0.075f)
                            .padding(bottom = 35.dp)
                            .clickable { onClick.invoke() })



                    Text(
                        text = "Google",
                        modifier = textModifier
                            .alpha(altAlphaValue)
                            .clickable { onClick.invoke() },
                        textAlign = TextAlign.Center,
                        fontSize = fontSize,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
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

                    Text(
                        text = "More",
                        modifier = textModifier
                            .alpha(alphaValue)
                            .clickable { onClick.invoke() },
                        textAlign = TextAlign.Center,
                        fontSize = fontSize,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
                }
                Box(contentAlignment = Alignment.Center) {

                    LaunchedEffect(uiStateHolder.getPagerState()?.isScrollInProgress) {
                        isIdiomSaved.value = !quizState
                                &&
                                dataStoreHolder
                                    .getSavedIdioms()
                                    .contains(idiomTitle)
                                &&
                                idiomTitle.isNotEmpty()
                    }

                    val onClick: () -> Unit = {
                        scope.launch {
                            if (!isIdiomSaved.value) {
                                dataStoreHolder.addIdiomToSaved(idiomTitle)
                                isIdiomSaved.value = !isIdiomSaved.value
                            } else {
                                dataStoreHolder.removeSavedIdiom(idiomTitle)
                                isIdiomSaved.value = !isIdiomSaved.value
                            }
                        }
                    }

                    SavedButton(
                        modifier = Modifier.alpha(altAlphaValue)
                            .fillMaxSize(0.075f)
                            .padding(bottom = 35.dp)
                            .clickable { if (!quizState) onClick.invoke() },
                        clicked = isIdiomSaved.value
                    )

                    Text(
                        text = if (isIdiomSaved.value) "Saved" else "Save",
                        modifier = textModifier
                            .alpha(altAlphaValue)
                            .clickable { if (!quizState) onClick.invoke() },
                        textAlign = TextAlign.Center,
                        fontSize = fontSize,
                        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
                    )
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(dataHolder: DataStoreStateHolder,
                       uiStateHolder: FeedUiStateHolder,
                       sheetState: ModalBottomSheetState,
                       isIdiomSaved: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), color = Color.Black, width = 5.dp)
            .background(Color(125, 184, 107))
            .fillMaxSize(0.2f)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.fillMaxWidth()) {
            GreetingView(dataHolder)
        }

        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 15.dp)) {
            StreakView(dataHolder)
            Spacer(modifier = Modifier.padding(15.dp))
            QuizCompleteView(dataHolder)
            Spacer(modifier = Modifier.padding(15.dp))
            SavedIdiomView(dataHolder, uiStateHolder, sheetState, isIdiomSaved)
        }
    }
}