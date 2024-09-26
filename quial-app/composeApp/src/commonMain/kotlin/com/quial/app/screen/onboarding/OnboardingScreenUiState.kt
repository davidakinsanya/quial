package com.quial.app.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.repository.OnboardingRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class
)
data class OnboardingScreenUiState constructor(val repository: OnboardingRepository) {
    suspend fun loadData(): List<Any> {
        return reorderMap(map = repository.getOnboardingMap())
    }

    private fun reorderMap(map: OnboardingMap?): List<Any> {
        val list = mutableListOf<Any>()
        if (map != null) {
            val maxQuestion = if (map.questionMap.isNotEmpty()) map.questionMap.keys.max() else 0
            val maxStatement = if (map.statementMap.isNotEmpty()) map.statementMap.keys.max() else 0

            if (maxQuestion > maxStatement) {
                for (num in 0..maxQuestion) {
                    if (num in map.questionMap.keys) {
                        list.add(map.questionMap[num] as Any)
                    } else {
                        list.add(map.statementMap[num] as Any)
                    }
                }
            }
        }

        return list
    }
}

// TODO: Load This UiState with data