package com.z1.pokedex.feature.home.presentation.model

data class PokemonPage(
    val count: Int = 0,
    val nextPage: String? = null,
    val previousPage: String? = null,
    val pokemonList: List<Pokemon> = emptyList()
)
