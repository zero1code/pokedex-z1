package com.z1.pokedex.navigation.navgraph.routes.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.feature.login.presentation.LoginContainer
import com.z1.pokedex.navigation.navgraph.routes.home.HomeNavGraph
import org.koin.androidx.compose.get

class LoginNavGraphImpl() : LoginNavGraph {
    private val baseRoute = "login"
    override fun route() = baseRoute
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            baseRoute
        ) {
            val homeNavGraph = get<HomeNavGraph>()
            LoginContainer(
                navigateToHomeScreen = {
                    navController.navigate(homeNavGraph.route()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}