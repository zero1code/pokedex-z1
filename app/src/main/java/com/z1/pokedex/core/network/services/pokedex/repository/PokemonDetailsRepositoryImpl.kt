package com.z1.pokedex.core.network.services.pokedex.repository

import com.z1.pokedex.core.network.mapper.asModel
import com.z1.pokedex.core.network.services.pokedex.PokedexClient
import kotlinx.coroutines.flow.flow

class PokemonDetailsRepositoryImpl(
    private val  pokedexClient: PokedexClient
): PokemonDetailsRepository {

    override suspend fun fetchPokemonDetails(pokemonName: String) =
        flow {
            emit(pokedexClient.fetchPokemonDetails(pokemonName).asModel())
        }
}