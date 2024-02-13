package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.presentation.model.Pokemon

data class UiState(
    val pokemonList: List<Pokemon> = emptyList()
)
