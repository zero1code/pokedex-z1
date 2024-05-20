package com.z1.pokedex.navigation.navgraph.routes.favorites

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.feature.favorites.presentation.FavoritesContainer

class FavoritesNavGraphImpl: FavoritesNavGraph {
    private val baseRoute = "favorites"
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
            FavoritesContainer(
               onNavigationIconClick = {
                   navController.popBackStack()
               }
            )
        }
    }
}