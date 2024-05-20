package com.z1.pokedex.navigation.navgraph.routes.subscription

import com.z1.pokedex.navigation.register.RegisterNavGraph

interface SubscriptionNavGraph: RegisterNavGraph {
    fun route(): String
}