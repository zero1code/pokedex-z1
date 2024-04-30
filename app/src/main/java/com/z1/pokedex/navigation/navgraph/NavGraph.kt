package com.z1.pokedex.navigation.navgraph

import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.navigation.navgraph.routes.login.LoginNavGraph

interface NavGraph {
    fun login(): LoginNavGraph
    fun home(): HomeNavGraph
}