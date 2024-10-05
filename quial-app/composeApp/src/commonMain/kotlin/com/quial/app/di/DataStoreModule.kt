package com.quial.app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.quial.app.data.datastore.DATA_STORE_FILE_NAME
import com.quial.app.data.datastore.DataStoreStateHolder
import com.quial.app.data.datastore.createDataStore
import org.koin.dsl.module

val dataStoreModule = module {

    single<DataStore<Preferences>> {
        createDataStore(get())
    }

    single<DataStoreStateHolder> {
        DataStoreStateHolder(createDataStore { DATA_STORE_FILE_NAME })
    }
}