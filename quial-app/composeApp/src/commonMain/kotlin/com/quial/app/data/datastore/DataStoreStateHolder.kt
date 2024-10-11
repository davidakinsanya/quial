package com.quial.app.data.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mmk.kmprevenuecat.purchases.Purchases
import com.quial.app.screens.onboarding.comps.OnboardingResponse
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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

    fun appendDailyCount(currentCount: Int) {
        uiStateHolderScope.launch {
            preferences.edit {
                val dailyCountTimeStamp = stringPreferencesKey("dailyCountTimeStamp")
                val countKey = intPreferencesKey("dailyFreeCount")

                it[countKey] = 0

                if (it[dailyCountTimeStamp]?.let { stamp -> sameDateCheck(stamp) } == false || currentCount == 0) {
                    it[dailyCountTimeStamp] = getCurrentDate()
                    it[countKey] = 0
                }
                // if (!checkCountLimit()) it[countKey] = currentCount + 1 TODO: Fix this ..
            }
        }
    }

    fun checkCountLimit(): Boolean {
        var count = 0
        uiStateHolderScope.launch {
            preferences.edit {
                val countKey = intPreferencesKey("dailyFreeCount")
                count = if (it[countKey] != null) it[countKey]!! else 0
            }
            println(count)
        }
        return if (isPremium()) false else count > 5
    }

    private fun getPurchasesData() = uiStateHolderScope.launch {
        preferences.edit {
            val isPremium = booleanPreferencesKey("isPremium")
            var boolean = false
            Purchases.syncPurchases { customerInfo ->
                boolean = customerInfo.getOrNull()?.entitlements.toString().isNotEmpty()
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


    private fun getCurrentDate(): String {
        val now: Instant = Clock.System.now()
        return now.toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    }

    private fun sameDateCheck(timestamp: String): Boolean {
        val stampDate = timestamp.split("T")[0].split("-")
        val currentDate = getCurrentDate().split("T")[0].split("-")
        return currentDate.toString() == stampDate.toString()
    }
}