package com.z1.pokedex.core.network.services.pokedex.repository

import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonDetailsRepository {
    suspend fun fetchPokemonDetails(pokemonName: String): Flow<PokemonDetails>
}