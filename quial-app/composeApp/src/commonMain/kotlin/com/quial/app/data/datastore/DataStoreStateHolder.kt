package com.quial.app.data.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.quial.app.screen.onboarding.comps.OnboardingResponse
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
                bool = it[response]?.isEmpty() ?: false
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

          val responseString = json.encodeToJsonElement(onboardingResponse).toString()
          // it[response] = responseString
      }
    }

    fun dailyFreeCount(currentCount: Int) = uiStateHolderScope.launch {
        preferences.edit {
            val dailyCountTimeStamp = stringPreferencesKey("dailyCountTimeStamp")
            val countKey = intPreferencesKey("dailyFreeCount")

            if (it[dailyCountTimeStamp]?.let { stamp -> sameDateCheck(stamp) } == false || currentCount == 0) {
                it[dailyCountTimeStamp] = getCurrentDate()
                it[countKey] = 0
            }

            // it[countKey] = currentCount + 1
        }
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