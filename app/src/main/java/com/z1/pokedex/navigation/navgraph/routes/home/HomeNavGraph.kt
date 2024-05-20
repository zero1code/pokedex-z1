package com.z1.pokedex.navigation.navgraph.routes.home

import com.z1.pokedex.navigation.register.RegisterNavGraph

interface HomeNavGraph: RegisterNavGraph {
    fun route(): String
}