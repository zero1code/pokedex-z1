package com.z1.pokedex.feature.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.z1.pokedex.feature.favorites.presentation.screen.FavoritesScreen

@Composable
fun FavoritesContainer(
    modifier: Modifier = Modifier
) {
    FavoritesScreen(
        onNavigationIconClick = {}
    )
}