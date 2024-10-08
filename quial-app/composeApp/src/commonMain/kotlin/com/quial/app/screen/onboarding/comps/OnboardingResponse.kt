package com.quial.app.screen.onboarding.comps

import com.quial.app.data.onboarding.Question
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingResponse(val question: Question,
                              val answerIndex: Int)