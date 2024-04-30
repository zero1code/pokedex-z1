package com.z1.pokedex.navigation.navgraph.routes.login

import com.z1.pokedex.navigation.register.RegisterNavGraph

interface LoginNavGraph: RegisterNavGraph {
    fun route(): String
}