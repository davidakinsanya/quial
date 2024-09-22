package com.quial.app.di

import org.koin.core.module.Module

internal expect val platformModule: Module
val appModules: List<Module>
    get() = listOf(onboardingModule)