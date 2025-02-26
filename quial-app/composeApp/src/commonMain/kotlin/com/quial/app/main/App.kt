package com.quial.app.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.navigation.RootAppDestination
import com.quial.app.navigation.RootAppNavigation
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(dataHolder: DataStoreStateHolder) {

    MaterialTheme {
        RootAppDestination.setDataHolder(dataHolder)
        RootAppNavigation(dataHolder)
    }
}


@Composable
fun RootAppNavigation(dataHolder: DataStoreStateHolder) {

    val startDestination = if (dataHolder.isOnboardingShown())
        RootAppDestination.Auth
    else
        RootAppDestination.Onboarding

    RootAppNavigation(startDestination = startDestination)
}