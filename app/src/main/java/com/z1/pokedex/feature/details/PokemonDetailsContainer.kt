package com.z1.pokedex.feature.details

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.feature.details.screen.PokemonDetailsScreen
import com.z1.pokedex.feature.details.screen.viewmodel.PokemonDetailsViewModel
import com.z1.pokedex.feature.home.domain.model.Pokemon
import org.koin.androidx.compose.getViewModel

@Composable
fun PokemonDetailsContainer(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onNavigationIconClick: () -> Unit
) {
    val viewModel: PokemonDetailsViewModel = getViewModel()
    val onEvent = viewModel::onEvent
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PokemonDetailsScreen(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        pokemon = pokemon,
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onNavigationIconClick = onNavigationIconClick,
    )
}