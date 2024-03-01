package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.feature.home.presentation.model.Pokemon
import com.z1.pokedex.feature.home.presentation.model.PokemonPage

data class UiState(
    val selectedPokemon: Pokemon = Pokemon(""),
    val pokemonPage: PokemonPage = PokemonPage()
)
