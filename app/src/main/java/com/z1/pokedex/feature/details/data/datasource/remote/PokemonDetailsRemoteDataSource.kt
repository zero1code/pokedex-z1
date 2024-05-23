package com.z1.pokedex.feature.details.data.datasource.remote

import com.z1.pokedex.core.network.model.PokemonDetailsDTO

interface PokemonDetailsRemoteDataSource {
    suspend fun fetchPokemonDetails(pokemonName: String): PokemonDetailsDTO
}