package com.z1.pokedex.core.navigation.navgraph.routes.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.core.navigation.navgraph.routes.login.LoginNavGraph
import com.z1.pokedex.feature.home.presentation.HomeContainer
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

class HomeNavGraphImpl(): HomeNavGraph {
    private val baseRoute = "home"
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
            val loginNavGraph = get<LoginNavGraph>()
            HomeContainer(
                navigateToLogin = {
                    navController.navigate(loginNavGraph.route()) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                drawerNavigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }
    }
}