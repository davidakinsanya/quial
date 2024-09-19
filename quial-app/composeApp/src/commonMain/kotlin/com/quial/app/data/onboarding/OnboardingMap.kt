package com.quial.app.data.onboarding

import com.quial.app.http.onboarding.dto.Statement
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingMap(val questionMap: HashMap<Int, Question>,
                         val statementMap: HashMap<Int, Statement>) {

    override fun toString(): String {
        var string = ""

        val maxQuestion = if (questionMap.isNotEmpty()) questionMap.keys.max() else 0
        val maxStatement = if(statementMap.isNotEmpty()) statementMap.keys.max() else 0

        if (maxQuestion > maxStatement) {
            for (num in 0..maxQuestion) {
                if (num in questionMap.keys) {
                    string += questionMap[num]
                    string += "\n"
                } else {
                    string += statementMap[num]
                    string += "\n"
                }
            }

        } else {
            for (num in 0..maxStatement) {
                if (num in questionMap.keys) {
                    string += questionMap[num]
                    string += "\n"
                } else {
                    string += statementMap[num]
                    string += "\n"
                }
            }
        }
        return string
    }

}
