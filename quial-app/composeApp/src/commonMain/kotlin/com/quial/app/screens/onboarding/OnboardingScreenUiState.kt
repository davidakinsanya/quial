package com.quial.app.screens.onboarding

import com.quial.app.data.onboarding.OnboardingMap
import com.quial.app.repository.OnboardingRepository

data class OnboardingScreenUiState(
    val repository: OnboardingRepository
) {
    suspend fun loadData(): List<Any> {
        return reorderMap(map = repository.getOnboardingMap())
    }

    private fun reorderMap(map: OnboardingMap?): List<Any> {
        val list = mutableListOf<Any>()

        if (map.toString().isNotEmpty()) {
            val maxQuestion = if (map!!.questionMap.isNotEmpty()) map.questionMap.keys.max() else 0
            val maxStatement = if (map.statementMap.isNotEmpty()) map.statementMap.keys.max() else 0

            if (maxQuestion > maxStatement) {
                for (num in 0..maxQuestion) {
                    if (num in map.questionMap.keys) {
                        list.add(map.questionMap[num] as Any)
                    } else {
                        list.add(map.statementMap[num] as Any)
                    }
                }
            }  else {
                for (num in 0..maxStatement) {
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