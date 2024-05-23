package com.z1.pokedex.feature.details.presentation.screen

import androidx.compose.runtime.Immutable
import com.z1.pokedex.feature.details.domain.model.PokemonDetails

@Immutable
data class PokemonDetailsUiState(
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val pokemonFavoritesNameList: List<String> = emptyList(),
    val pokemonDetails: PokemonDetails? = null,
) {
    fun isFavorite(pokemonName: String) = pokemonFavoritesNameList.contains(pokemonName)
}
