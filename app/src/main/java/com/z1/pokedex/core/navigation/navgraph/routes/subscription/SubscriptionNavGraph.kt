package com.z1.pokedex.core.navigation.navgraph.routes.subscription

import com.z1.pokedex.core.navigation.register.RegisterNavGraph

interface SubscriptionNavGraph : RegisterNavGraph {
    fun route(): String
}