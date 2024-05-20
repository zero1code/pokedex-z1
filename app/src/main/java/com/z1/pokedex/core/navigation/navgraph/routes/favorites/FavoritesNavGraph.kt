package com.z1.pokedex.core.navigation.navgraph.routes.favorites

import com.z1.pokedex.core.navigation.register.RegisterNavGraph

interface FavoritesNavGraph: RegisterNavGraph {
    fun route(): String
}