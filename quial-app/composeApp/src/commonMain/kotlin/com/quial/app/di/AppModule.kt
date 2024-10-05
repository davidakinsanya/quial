package com.quial.app.di

import com.quial.app.getPlatform
import org.koin.core.module.Module
import org.koin.dsl.module


val platformModule: Module = module {
    single {
        getPlatform()
    }
}

val appModules: List<Module>
    get() = listOf(platformModule,
                   ktorModule,
                   dataStoreModule,
                   onboardingModule,
                   feedModule)
