package com.quial.app

import com.quial.app.di.appModules
import com.quial.app.di.ktorModule
import com.quial.app.di.onboardingModule
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.Test

class KoinModuleTest: KoinTest  {

    @Test
    fun checkAllModules() {
        checkModules { appModules }
    }

    @Test
    fun checkKtorModule() {
        checkModules { ktorModule }
    }

    @Test
    fun checkOnboardingModule() {
        checkModules { onboardingModule }
    }
}