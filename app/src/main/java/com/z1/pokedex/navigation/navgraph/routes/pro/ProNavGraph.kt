package com.z1.pokedex.navigation.navgraph.routes.pro

import com.z1.pokedex.navigation.register.RegisterNavGraph

interface ProNavGraph: RegisterNavGraph {
    fun route(): String
}