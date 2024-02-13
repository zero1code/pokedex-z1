package com.z1.pokedex

import android.app.Application
import com.z1.pokedex.core.network.di.networkModule
import com.z1.pokedex.feature.home.di.homeScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        bindKoin()
    }

    private fun bindKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                networkModule +
                homeScreenModule
            )
        }
    }
}