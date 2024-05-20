package com.z1.pokedex.core.navigation.navgraph.routes.login

import com.z1.pokedex.core.navigation.register.RegisterNavGraph

interface LoginNavGraph: RegisterNavGraph {
    fun route(): String
}