package com.quial.app

import androidx.compose.ui.window.ComposeUIViewController
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.main.App

fun MainViewController() = ComposeUIViewController {
    App(dataHolder = DataStoreStateHolder(createDataStore()))
}