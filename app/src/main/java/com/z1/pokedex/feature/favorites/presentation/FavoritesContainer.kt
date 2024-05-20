package com.z1.pokedex.feature.favorites.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.feature.favorites.presentation.screen.FavoritesScreen
import com.z1.pokedex.feature.favorites.presentation.screen.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoritesContainer(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    val viewModel: FavoritesViewModel = getViewModel()
    val onEvent = viewModel::onEvent
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreen(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick
    )
}