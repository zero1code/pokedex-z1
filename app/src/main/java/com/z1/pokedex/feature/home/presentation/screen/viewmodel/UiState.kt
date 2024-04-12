package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonPage

data class UiState(
    val pokemonPage: PokemonPage = PokemonPage(),
    val pokemonDetails: PokemonDetails? = null,
    val isLoadingPage: Boolean = false,
    val isFirstLoading: Boolean = true,
    val pokemonClickedList: Set<String> = emptySet()
)
