package com.quial.app.data.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mmk.kmprevenuecat.purchases.Purchases
import com.quial.app.screens.onboarding.comps.OnboardingResponse
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.getCurrentDate
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class DataStoreStateHolder(
    private val preferences: DataStore<Preferences>
): UiStateHolder() {

    fun getPref(): DataStore<Preferences> {
        return preferences
    }

    fun isOnboardingShown(): Boolean  {
        var bool = false
        runBlocking {
            preferences.edit {
                val response = stringPreferencesKey("onboardingResponse")
                // it[response] = ""
                bool = it[response]?.isNotEmpty() == true
            }
        }

        return bool
    }

    fun saveOnboardingResponse(onboardingResponse: List<OnboardingResponse>) = uiStateHolderScope.launch {
      preferences.edit {
          val json = Json {
              ignoreUnknownKeys = true
              isLenient = false
          }

          val response = stringPreferencesKey("onboardingResponse")
          val responseString = json.encodeToJsonElement(onboardingResponse).toString()
          it[response] = responseString
      }
    }

    private fun getPurchasesData() = uiStateHolderScope.launch {
        preferences.edit {
            val isPremium = booleanPreferencesKey("isPremium")
            var boolean = false
            Purchases.syncPurchases { customerInfo ->
                boolean = customerInfo.getOrNull()?.entitlements?.all?.size == 1
                println("Info: " + customerInfo.getOrNull()?.entitlements?.all?.size)
            }
            it[isPremium] = boolean
        }
    }

    fun isPremium(): Boolean {
        var boolean = false
        uiStateHolderScope.launch {
            getPurchasesData()
            preferences.edit {
                val isPremium = booleanPreferencesKey("isPremium")
                boolean = it[isPremium] == true
            }
        }
        return boolean
    }

    fun updateTimeStamp() = uiStateHolderScope.launch {
        preferences.edit {
            val stamp = stringPreferencesKey("timeStamp")
            it[stamp] = getCurrentDate()
        }
    }
}