package com.quial.app.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.quial.app.utils.UiStateHolder

class DataStoreStateHolder(preferences: DataStore<Preferences>): UiStateHolder() {

}