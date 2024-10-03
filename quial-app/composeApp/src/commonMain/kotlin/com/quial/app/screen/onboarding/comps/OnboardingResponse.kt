package com.quial.app.screen.onboarding.comps

import com.quial.app.data.onboarding.Question

data class OnboardingResponse(val question: Question,
                              val answerIndex: Int)