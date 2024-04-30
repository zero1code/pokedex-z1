package com.z1.pokedex.navigation.navgraph

import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph

class NavGraphImpl(
    private val loginNavGraph: LoginNavGraph,
    private val homeNavGraph: HomeNavGraph
): NavGraph {
    override fun login() = loginNavGraph
    override fun home() = homeNavGraph
}