package com.z1.pokedex.navigation.util

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.z1.pokedex.navigation.register.RegisterNavGraph

fun NavGraphBuilder.register(
    modifier: Modifier = Modifier,
    registerNavGraph: RegisterNavGraph,
    navController: NavHostController
) {
    registerNavGraph.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier
    )
}