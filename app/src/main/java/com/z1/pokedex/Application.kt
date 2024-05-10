package com.z1.pokedex

import android.app.Application
import com.z1.pokedex.core.database.di.databaseModule
import com.z1.pokedex.core.datasource.di.datasourceModule
import com.z1.pokedex.core.network.di.networkModule
import com.z1.pokedex.feature.home.di.homeScreenModule
import com.z1.pokedex.feature.login.di.loginModule
import com.z1.pokedex.feature.subscription.di.subscriptionModule
import com.z1.pokedex.navigation.di.navigationModule
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
                listOf(
                    datasourceModule,
                    networkModule,
                    homeScreenModule,
                    loginModule,
                    navigationModule,
                    subscriptionModule,
                    databaseModule
                ).flatten()
            )
        }
    }
}