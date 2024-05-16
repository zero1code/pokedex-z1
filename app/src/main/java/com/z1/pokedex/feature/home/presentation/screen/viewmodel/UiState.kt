package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import androidx.compose.runtime.Immutable
import com.z1.pokedex.core.network.model.google.UserData
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.model.PokemonDetails

@Immutable
data class UiState(
    val pokemonPage: List<Pokemon> = emptyList(),
    val pokemonDetails: PokemonDetails? = null,
    val isLastPage: Boolean = false,
    val isLoadingPage: Boolean = false,
    val isFirstLoading: Boolean = true,
    val isConnected: Boolean = false,
    val pokemonClickedList: Set<String> = emptySet(),
    val pokemonFavoritesNameList: List<String> = emptyList(),
    val userData: UserData? = UserData("", null, null)
) {
    fun canLoadNextPage() = isLoadingPage.not() && isLastPage.not()
}
