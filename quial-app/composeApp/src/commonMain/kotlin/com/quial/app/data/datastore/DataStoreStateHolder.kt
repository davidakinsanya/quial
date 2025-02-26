package com.quial.app.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.quial.app.http.requests.StripeClient
import com.quial.app.screens.onboarding.comps.OnboardingResponse
import com.quial.app.utils.UiStateHolder
import com.quial.app.utils.getCurrentDate
import com.quial.app.utils.uiStateHolderScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class DataStoreStateHolder(
    private val preferences: DataStore<Preferences>,
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

    suspend fun setUserEmail(email: String): Boolean {
        var bool = false

        withContext(Dispatchers.IO) {
            preferences.edit {
                val response = stringPreferencesKey("user_email")
                bool = it[response]?.isNotEmpty() == true
                if (!bool) {
                    it[response] = email
                }
            }
        }

        return bool
    }

    suspend fun getEmail(): String {
        var email = ""

        withContext(Dispatchers.IO) {
            preferences.edit {
                val response = stringPreferencesKey("user_email")
                email = if (it[response].isNullOrEmpty()) "" else it[response]!!
            }
        }

        return email
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


     suspend fun isPremium(stripeClient: StripeClient): Boolean {
        val id = stripeClient.getCustomer(getEmail())
        return stripeClient.getSubscription(id)
    }

    fun updateTimeStamp() = uiStateHolderScope.launch {
        preferences.edit {
            val stamp = stringPreferencesKey("timeStamp")
            it[stamp] = getCurrentDate()
        }
    }
}