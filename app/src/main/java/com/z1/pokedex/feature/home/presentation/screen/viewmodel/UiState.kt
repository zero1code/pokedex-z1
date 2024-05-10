package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.core.network.model.google.UserData
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.model.PokemonDetails

data class UiState(
    val pokemonPage: List<Pokemon> = emptyList(),
    val pokemonDetails: PokemonDetails? = null,
    val isLastPage: Boolean = false,
    val isLoadingPage: Boolean = false,
    val isFirstLoading: Boolean = true,
    val isConnected: Boolean = false,
    val pokemonClickedList: Set<String> = emptySet(),
    val userData: UserData? = UserData("", null, null)
) {
    fun canLoadNextPage() = isLoadingPage.not() && isLastPage.not()
//        when {
//            isLoadingPage.not() && isLastPage && isConnected.not() -> false
//            isLoadingPage.not() && isLastPage.not() && isConnected.not() -> true
//            isLoadingPage.not() && isLastPage.not() && isConnected -> true
//            isLoadingPage.not() && isLastPage && isConnected.not() -> false
//            isLoadingPage.not() && isLastPage && isConnected -> false
//            else -> false
//        }
}
