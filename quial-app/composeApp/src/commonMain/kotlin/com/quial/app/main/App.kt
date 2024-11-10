package com.quial.app.main

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.navigation.RootAppDestination
import com.quial.app.navigation.RootAppNavigation
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
        RootAppDestination.Feed
    else
        RootAppDestination.Onboarding

    RootAppNavigation(startDestination = startDestination)
}