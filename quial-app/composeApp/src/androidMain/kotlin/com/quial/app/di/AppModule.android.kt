package com.quial.app.di

import org.koin.core.module.Module

internal actual val platformModule: Module
    get() {
        TODO()
    }
val appModules: List<Module>
    get() =
       listOf(onboardingModule)