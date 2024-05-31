package com.z1.pokedex

import android.app.Application
import com.z1.pokedex.core.di.database.databaseModule
import com.z1.pokedex.core.di.feature.detailsScreenModule
import com.z1.pokedex.core.di.feature.favoritesModule
import com.z1.pokedex.core.di.feature.homeScreenModule
import com.z1.pokedex.core.di.feature.loginModule
import com.z1.pokedex.core.di.feature.subscriptionModule
import com.z1.pokedex.core.di.navigation.navigationModule
import com.z1.pokedex.core.di.network.networkModule
import com.z1.pokedex.core.di.network.networkRepositoryModule
import com.z1.pokedex.core.di.shared.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        bindKoin()
    }

    private fun bindKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    networkModule,
                    homeScreenModule,
                    loginModule,
                    navigationModule,
                    subscriptionModule,
                    databaseModule,
                    favoritesModule,
                    detailsScreenModule,
                    networkRepositoryModule,
                    sharedModule
                ).flatten()
            )
        }
    }
}