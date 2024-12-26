@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.quial.app

import androidx.compose.ui.window.ComposeUIViewController
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.main.App
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import com.quial.app.data.datastore.DATA_STORE_FILE_NAME

fun MainViewController() = ComposeUIViewController {
    App(dataHolder = DataStoreStateHolder(createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
    }))
}
