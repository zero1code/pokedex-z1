package com.z1.pokedex.feature.favorites.presentation.screen

import androidx.compose.runtime.Immutable
import com.z1.pokedex.feature.home.domain.model.Pokemon

@Immutable
data class FavoritesScreenUiState(
    val isLoading: Boolean = true,
    val isConnected: Boolean = false,
    val favorites: List<Pokemon> = emptyList()
)