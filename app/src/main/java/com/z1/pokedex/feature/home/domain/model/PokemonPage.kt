package com.z1.pokedex.feature.home.domain.model

data class PokemonPage(
    val count: Int = 0,
    val nextPage: String? = "0",
    val previousPage: String? = null,
    val pokemonList: List<Pokemon> = emptyList()
)
