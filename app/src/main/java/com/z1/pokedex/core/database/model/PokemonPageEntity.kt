package com.z1.pokedex.core.database.model

data class PokemonPageEntity(
    val count: Int = 0,
    val nextPage: String? = "0",
    val previousPage: String? = null,
    val pokemonList: List<PokemonEntity> = emptyList()
)
