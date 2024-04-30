package com.z1.pokedex.navigation.di

import com.z1.pokedex.navigation.navgraph.NavGraph
import com.z1.pokedex.navigation.navgraph.NavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraphImpl
import org.koin.dsl.module

private val provideNavGraph = module {
    single<NavGraph>{ NavGraphImpl(get(), get()) }
}

private val provideLoginNavGraph = module {
    factory<LoginNavGraph> { LoginNavGraphImpl() }
}

private val provideHomeNavGraph = module {
    factory<HomeNavGraph>{ HomeNavGraphImpl() }
}

val navigationModule = listOf(
    provideNavGraph,
    provideLoginNavGraph,
    provideHomeNavGraph
)