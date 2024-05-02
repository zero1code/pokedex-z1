package com.z1.pokedex.navigation.navgraph

import com.z1.pokedex.navigation.navgraph.routes.favorites.FavoritesNavGraph
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.navigation.navgraph.routes.pro.ProNavGraph

class NavGraphImpl(
    private val loginNavGraph: LoginNavGraph,
    private val homeNavGraph: HomeNavGraph,
    private val favoritesNavGraph: FavoritesNavGraph,
    private val proNavGraph: ProNavGraph
): NavGraph {
    override fun login() = loginNavGraph
    override fun home() = homeNavGraph
    override fun favorites() = favoritesNavGraph
    override fun pro() = proNavGraph
}