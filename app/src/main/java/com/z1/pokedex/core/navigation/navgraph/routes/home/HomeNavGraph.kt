package com.z1.pokedex.core.navigation.navgraph.routes.home

import com.z1.pokedex.core.navigation.register.RegisterNavGraph

interface HomeNavGraph: RegisterNavGraph {
    fun route(): String
}