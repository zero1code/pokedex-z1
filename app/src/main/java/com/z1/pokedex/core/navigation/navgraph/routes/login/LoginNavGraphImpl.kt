package com.z1.pokedex.core.navigation.navgraph.routes.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.core.navigation.navgraph.routes.home.HomeNavGraph
import com.z1.pokedex.feature.login.presentation.LoginContainer
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
            baseRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(600, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(600, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(600, easing = LinearEasing)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(600, easing = LinearEasing)
                )
            }
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