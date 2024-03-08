package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.presentation.model.PokemonPage

data class UiState(
    val pokemonPage: PokemonPage = PokemonPage(),
    val isLoadingPage: Boolean = false,
    val isFirstLoading: Boolean = true
)
