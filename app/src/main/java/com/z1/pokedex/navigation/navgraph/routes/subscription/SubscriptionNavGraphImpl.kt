package com.z1.pokedex.navigation.navgraph.routes.subscription

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.z1.pokedex.feature.subscription.presentation.SubscriptionContainer

class SubscriptionNavGraphImpl: SubscriptionNavGraph {
    private val baseRoute = "subscription"
    override fun route() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            SubscriptionContainer(
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}