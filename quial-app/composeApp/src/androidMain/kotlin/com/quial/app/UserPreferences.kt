package com.quial.app

import android.content.Context
import com.quial.app.data.datastore.DataStoreStateHolder

object UserPreferences {
    fun getInstance(context: Context): DataStoreStateHolder {
        return  DataStoreStateHolder(createDataStore(context))
    }
}