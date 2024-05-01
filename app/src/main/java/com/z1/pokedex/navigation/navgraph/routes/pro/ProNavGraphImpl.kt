package com.z1.pokedex.navigation.navgraph.routes.pro

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.feature.pro.presentation.ProContainer

class ProNavGraphImpl: ProNavGraph {
    private val baseRoute = "pro"
    override fun route() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            ProContainer()
        }
    }
}