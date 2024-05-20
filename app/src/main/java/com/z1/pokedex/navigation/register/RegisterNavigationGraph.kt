package com.z1.pokedex.navigation.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.z1.pokedex.navigation.navgraph.NavGraph
import com.z1.pokedex.navigation.util.register

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navGraph: NavGraph,
    isLogged: Boolean
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination =
        if (isLogged) navGraph.home().route()
        else navGraph.login().route()
    ) {
        register(
            modifier = modifier,
            registerNavGraph = navGraph.login(),
            navController = navController
        )

        register(
            modifier = modifier,
            registerNavGraph = navGraph.home(),
            navController = navController
        )

        register(
            modifier = modifier,
            registerNavGraph = navGraph.favorites(),
            navController = navController
        )

        register(modifier = modifier,
            registerNavGraph = navGraph.subscription(),
            navController = navController
        )
    }
}

