package com.z1.pokedex.navigation.di

import com.z1.pokedex.navigation.navgraph.NavGraph
import com.z1.pokedex.navigation.navgraph.NavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.favorites.FavoritesNavGraph
import com.z1.pokedex.navigation.navgraph.routes.favorites.FavoritesNavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraphImpl
import com.z1.pokedex.navigation.navgraph.routes.pro.ProNavGraph
import com.z1.pokedex.navigation.navgraph.routes.pro.ProNavGraphImpl
import org.koin.dsl.module

private val provideNavGraph = module {
    single<NavGraph>{ NavGraphImpl(get(), get(), get(), get()) }
}

private val provideLoginNavGraph = module {
    factory<LoginNavGraph> { LoginNavGraphImpl() }
}

private val provideHomeNavGraph = module {
    factory<HomeNavGraph>{ HomeNavGraphImpl() }
}

private val provideFavoritesNavGraph = module {
    factory<FavoritesNavGraph>{ FavoritesNavGraphImpl() }
}

private val provideProNavGraph = module {
    factory<ProNavGraph>{ ProNavGraphImpl() }
}

val navigationModule = listOf(
    provideNavGraph,
    provideLoginNavGraph,
    provideHomeNavGraph,
    provideFavoritesNavGraph,
    provideProNavGraph
)