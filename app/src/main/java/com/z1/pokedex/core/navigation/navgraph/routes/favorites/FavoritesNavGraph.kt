package com.z1.pokedex.navigation.navgraph.routes.favorites

import com.z1.pokedex.navigation.register.RegisterNavGraph

interface FavoritesNavGraph: RegisterNavGraph {
    fun route(): String
}