package com.z1.pokedex.core.di.navigation

import com.z1.pokedex.core.navigation.navgraph.NavGraph
import com.z1.pokedex.core.navigation.navgraph.NavGraphImpl
import com.z1.pokedex.core.navigation.navgraph.routes.favorites.FavoritesNavGraph
import com.z1.pokedex.core.navigation.navgraph.routes.favorites.FavoritesNavGraphImpl
import com.z1.pokedex.core.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.core.navigation.navgraph.routes.home.HomeNavGraphImpl
import com.z1.pokedex.core.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.core.navigation.navgraph.routes.login.LoginNavGraphImpl
import com.z1.pokedex.core.navigation.navgraph.routes.subscription.SubscriptionNavGraph
import com.z1.pokedex.core.navigation.navgraph.routes.subscription.SubscriptionNavGraphImpl
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

private val provideSubscriptionNavGraph = module {
    factory<SubscriptionNavGraph>{ SubscriptionNavGraphImpl() }
}

val navigationModule = listOf(
    provideNavGraph,
    provideLoginNavGraph,
    provideHomeNavGraph,
    provideFavoritesNavGraph,
    provideSubscriptionNavGraph
)