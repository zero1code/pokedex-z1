package com.z1.pokedex.feature.details.domain.repository

import com.z1.pokedex.feature.details.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {
    suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>>
    suspend fun fetchPokemonDetails(pokemonName: String): PokemonDetails
}