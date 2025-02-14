package com.quial.app.data.datastore


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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

    fun setUserEmail(email: String): Boolean {
        var bool = false

        runBlocking {
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

    fun getEmail(): String {
        var email = ""

        runBlocking {
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

    @Composable
    private fun getPurchasesData(): Boolean {
        val purchase = remember {  mutableStateOf(false) }
        Purchases.getCustomerInfo { customerInfo ->
            customerInfo.getOrNull()?.entitlements?.all?.values?.iterator()?.forEach {
                if (!purchase.value && it.isActive) purchase.value = true
            }
        }
        return purchase.value
    }

    @Composable
    fun isPremium(): Boolean {
        return getPurchasesData()
    }

    fun updateTimeStamp() = uiStateHolderScope.launch {
        preferences.edit {
            val stamp = stringPreferencesKey("timeStamp")
            it[stamp] = getCurrentDate()
        }
    }
}