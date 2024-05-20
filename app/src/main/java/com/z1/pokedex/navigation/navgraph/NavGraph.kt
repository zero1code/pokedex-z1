package com.z1.pokedex.navigation.navgraph

import com.z1.pokedex.navigation.navgraph.routes.favorites.FavoritesNavGraph
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.navigation.navgraph.routes.subscription.SubscriptionNavGraph

interface NavGraph {
    fun login(): LoginNavGraph
    fun home(): HomeNavGraph
    fun favorites(): FavoritesNavGraph
    fun subscription(): SubscriptionNavGraph
}