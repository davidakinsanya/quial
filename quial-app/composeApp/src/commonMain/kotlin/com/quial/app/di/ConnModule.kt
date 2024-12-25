package com.quial.app.di

import com.plusmobileapps.konnectivity.Konnectivity
import org.koin.dsl.module

val connModule = module {
    single<Konnectivity> {
        Konnectivity()
    }
}